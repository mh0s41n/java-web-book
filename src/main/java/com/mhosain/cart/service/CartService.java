package com.mhosain.cart.service;

import com.mhosain.cart.domain.Cart;
import com.mhosain.cart.domain.User;

public interface CartService {
    Cart getCartByUser(User currentUser);

    void addProductToCart(String productId, Cart cart);
    void removeProductFromCart(String productId, Cart cart);

    void deleteProductFromCart(String productId, Cart cart);
}
