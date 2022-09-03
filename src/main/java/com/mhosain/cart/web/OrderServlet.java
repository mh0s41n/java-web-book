package com.mhosain.cart.web;

import com.mhosain.cart.dto.ShippingAddressDTO;
import com.mhosain.cart.repository.*;
import com.mhosain.cart.service.CartService;
import com.mhosain.cart.service.CartServiceImpl;
import com.mhosain.cart.service.OrderService;
import com.mhosain.cart.service.OrderServiceImpl;
import com.mhosain.cart.util.SecurityContext;
import com.mhosain.cart.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private CartService cartService = new CartServiceImpl(
            new CartRepositoryImpl(),
            new ProductRepositoryImpl(),
            new CartItemRepositoryImpl());

    private OrderService orderService = new OrderServiceImpl(
            new OrderRepositoryImpl(),
            new ShippingAddressRepositoryImpl(),
            new CartRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        addCartToUI(req);

        req.setAttribute("countries", getCountries());

        LOGGER.info("Serving order page");

        req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
    }

    private void addCartToUI(HttpServletRequest req) {
        if (SecurityContext.isAuthenticated(req)) {
            var currentUser = SecurityContext.getCurrentUser(req);
            var cart = cartService.getCartByUser(currentUser);
            req.setAttribute("cart", cart);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("Handle order request form");

        var shippingAddress = copyParametersTo(req);

        LOGGER.info("shippingAddress information: {}", shippingAddress);

        var errors = ValidationUtil.getInstance().validate(shippingAddress);

        if (!errors.isEmpty()) {
            req.setAttribute("countries", getCountries());
            req.setAttribute("errors", errors);
            req.setAttribute("shippingAddress", shippingAddress);
            addCartToUI(req);
            req.getRequestDispatcher("/WEB-INF/order.jsp").forward(req, resp);
        } else {
            orderService.processOrder(shippingAddress, SecurityContext.getCurrentUser(req));

            resp.sendRedirect("/home?orderSuccess=true");
        }
    }

    private ShippingAddressDTO copyParametersTo(HttpServletRequest req) {
        var shippingAddress = new ShippingAddressDTO();

        shippingAddress.setAddress(req.getParameter("address"));
        shippingAddress.setAddress2(req.getParameter("address2"));
        shippingAddress.setState(req.getParameter("state"));
        shippingAddress.setZip(req.getParameter("zip"));
        shippingAddress.setCountry(req.getParameter("country"));
        shippingAddress.setMobileNumber(req.getParameter("mobileNumber"));

        return shippingAddress;
    }

    private List<String> getCountries() {
        return List.of("Bangladesh", "Switzerland", "Japan", "USA", "Canada", "Uganda");
    }
}
