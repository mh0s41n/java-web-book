package com.mhosain.cart.web;

import com.mhosain.cart.domain.Cart;
import com.mhosain.cart.dto.ProductDTO;
import com.mhosain.cart.repository.CartItemRepositoryImpl;
import com.mhosain.cart.repository.CartRepositoryImpl;
import com.mhosain.cart.repository.ProductRepositoryImpl;
import com.mhosain.cart.service.CartService;
import com.mhosain.cart.service.CartServiceImpl;
import com.mhosain.cart.service.ProductService;
import com.mhosain.cart.service.ProductServiceImpl;
import com.mhosain.cart.util.SecurityContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(HomeServlet.class);
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    public static final CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new ProductRepositoryImpl(),
            new CartItemRepositoryImpl()
    );

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Serving home page");

        List<ProductDTO> allProducts = productService.findAllProductSortedByName();
        LOGGER.info("Total {} product found", allProducts.size());

        if (SecurityContext.isAuthenticated(request)) {
            var currentUser = SecurityContext.getCurrentUser(request);
            var cart = cartService.getCartByUser(currentUser);
            request.setAttribute("cart", cart);
        }

        request.setAttribute("products", allProducts);

        request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }
}
