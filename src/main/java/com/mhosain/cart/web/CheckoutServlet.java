package com.mhosain.cart.web;

import com.mhosain.cart.repository.CartItemRepositoryImpl;
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

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckoutServlet.class);

    private CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new ProductRepositoryImpl(),
            new CartItemRepositoryImpl()
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        LOGGER.info("Serving checkout page");

        var currentUser = SecurityContext.getCurrentUser(request);
        var cart = cartService.getCartByUser(currentUser);
        request.setAttribute("cart", cart);

        request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
    }
}
