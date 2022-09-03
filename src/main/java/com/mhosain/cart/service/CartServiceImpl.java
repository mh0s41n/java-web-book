package com.mhosain.cart.service;

import com.mhosain.cart.domain.Cart;
import com.mhosain.cart.domain.CartItem;
import com.mhosain.cart.domain.Product;
import com.mhosain.cart.domain.User;
import com.mhosain.cart.exceptions.CartNotFoundException;
import com.mhosain.cart.exceptions.ProductNotFoundException;
import com.mhosain.cart.repository.CartItemRepository;
import com.mhosain.cart.repository.CartRepository;
import com.mhosain.cart.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(CartRepository cartRepository,
                           ProductRepository productRepository,
                           CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public Cart getCartByUser(User currentUser) {
        Optional<Cart> optionalCart = cartRepository.findByUser(currentUser);

        return optionalCart.orElseGet(() -> createNewCart(currentUser));
    }

    @Override
    public void addProductToCart(String productId, Cart cart) {
        Product product = findProduct(productId);

        addProductToCart(product, cart);

        updateCart(cart);
    }

    @Override
    public void removeProductFromCart(String productId, Cart cart) {
        Product product = findProduct(productId);

        removeProductFromCart(product, cart);

        updateCart(cart);
    }

    @Override
    public void deleteProductFromCart(String productId, Cart cart) {
        Product product = findProduct(productId);

        deleteProductFromCart(product, cart);

        updateCart(cart);
    }

    private void addProductToCart(Product product, Cart cart) {
        var cartItemOptional = findSimilarProductInCart(cart, product);

        var cartItem = cartItemOptional.map(this::increaseQuantityByOne)
                .orElseGet(() -> createNewCartItem(product));

        cart.getCartItems().add(cartItem);
    }

    private void removeProductFromCart(Product product, Cart cart) {
        var itemOptional = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findAny();

        var cartItem = itemOptional
                .orElseThrow(() -> new CartNotFoundException("Cart item not found by product: " + product));

        if (cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartItem.setPrice(cartItem.getPrice().subtract(product.getPrice()));

            cart.getCartItems().add(cartItem);

            cartItemRepository.update(cartItem);
        } else {
            cart.getCartItems().remove(cartItem);
            cartItemRepository.remove(cartItem);
        }
    }

    private void deleteProductFromCart(Product product, Cart cart) {
        var itemOptional = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().equals(product)).findAny();

        var cartItem = itemOptional
                .orElseThrow(() -> new CartNotFoundException("Cart item not found by product: " + product));

        cart.getCartItems().remove(cartItem);
        cartItemRepository.remove(cartItem);
    }

    private Product findProduct(String productId) {
        if (productId == null || productId.length() == 0) {
            throw new IllegalArgumentException("Product id cannot be null");
        }
        Long id = parseProductId(productId);

        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found by id: " + id));
    }

    private void updateCart(Cart cart) {
        Integer totalItem = getTotalItem(cart);
        BigDecimal totalPrice = calculateTotalPrice(cart);

        cart.setTotalItem(totalItem);
        cart.setTotalPrice(totalPrice);

        cartRepository.update(cart);
    }

    private Cart createNewCart(User currentUser) {
        var cart = new Cart();
        cart.setUser(currentUser);

        return cartRepository.save(cart);
    }

    private Long parseProductId(String productId) {
        try {
            return Long.parseLong(productId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Product id must be a number", e);
        }
    }

    private CartItem createNewCartItem(Product product) {
        var cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItem.setPrice(product.getPrice());

        return cartItemRepository.save(cartItem);
    }

    private Optional<CartItem> findSimilarProductInCart(Cart cart, Product product) {
        return cart.getCartItems().stream().filter(cartItem -> cartItem.getProduct().equals(product)).findFirst();
    }

    private CartItem increaseQuantityByOne(CartItem cartItem) {
        cartItem.setQuantity(cartItem.getQuantity() + 1);

        BigDecimal totalPrice = cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        cartItem.setPrice(totalPrice);

        return cartItemRepository.update(cartItem);
    }

    private Integer getTotalItem(Cart cart) {
        return cart.getCartItems().stream().map(CartItem::getQuantity).reduce(0, Integer::sum);
    }

    private BigDecimal calculateTotalPrice(Cart cart) {
        return cart.getCartItems().stream().map(CartItem::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
