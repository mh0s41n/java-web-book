package com.mhosain.cart.web;

import com.mhosain.cart.domain.Cart;
import com.mhosain.cart.domain.User;
import com.mhosain.cart.repository.CartItemRepositoryImpl;
import com.mhosain.cart.repository.CartRepository;
import com.mhosain.cart.repository.CartRepositoryImpl;
import com.mhosain.cart.repository.ProductRepositoryImpl;
import com.mhosain.cart.service.CartService;
import com.mhosain.cart.service.CartServiceImpl;
import com.mhosain.cart.util.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/add-to-cart")
public class CartServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(CartServlet.class);

    private CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new ProductRepositoryImpl(),
            new CartItemRepositoryImpl()
    );

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        var productId = request.getParameter("productId");

        LOGGER.info("Received request to add product with id: {}", productId);

        var cart = getCart(request);
        cartService.addProductToCart(productId, cart);

        response.sendRedirect("/home");
    }

    private Cart getCart(HttpServletRequest request) {
        final User currentUser = SecurityContext.getCurrentUser(request);

        return cartService.getCartByUser(currentUser);
    }
}
