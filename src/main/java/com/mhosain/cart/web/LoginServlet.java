package com.mhosain.cart.web;

import com.mhosain.cart.dto.LoginDTO;
import com.mhosain.cart.exceptions.UserNotFoundException;
import com.mhosain.cart.repository.UserRepositoryImpl;
import com.mhosain.cart.service.UserService;
import com.mhosain.cart.service.UserServiceImpl;
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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Serving login page");
        var logout = request.getParameter("logout");
        if (Boolean.parseBoolean(logout)) {
            request.setAttribute("message", "You have been successfully logged out.");
        }

        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var loginDTO = new LoginDTO(request.getParameter("username"), request.getParameter("password"));

        LOGGER.info("Received login data: {}", loginDTO);

        var errors = ValidationUtil.getInstance().validate(loginDTO);

        if (!errors.isEmpty()) {
            LOGGER.info("Failed to login, sending login form again");

            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }

        try {
            login(loginDTO, request);

            LOGGER.info("Login successful, redirecting to home page");

            response.sendRedirect("/home");
        } catch (UserNotFoundException e) {
            LOGGER.error("incorrect username/password", e);

            errors.put("username", "incorrect username/password");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }

    private void login(LoginDTO loginDTO, HttpServletRequest request) throws UserNotFoundException {
        var user = userService.verifyUser(loginDTO);

        SecurityContext.login(request, user);
    }
}
