package com.mhosain.cart.web;

import com.mhosain.cart.dto.UserDTO;
import com.mhosain.cart.repository.UserRepositoryImpl;
import com.mhosain.cart.service.UserService;
import com.mhosain.cart.service.UserServiceImpl;
import com.mhosain.cart.util.ValidationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
    private final static Logger LOGGER = LoggerFactory.getLogger(SignupServlet.class);

    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LOGGER.info("Serving signup page");

        request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        var userDTO = copyParametersTo(request);

        var errors = ValidationUtil.getInstance().validate(userDTO);

        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("userDTO", userDTO);

            LOGGER.info("User sent invalid data: {}", userDTO);

            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        } else if (userService.isNotUniqueUsername(userDTO)) {
            LOGGER.info("Username: {} is already exists", userDTO.getUsername());

            errors.put("username", "already taken, please use a different one");

            request.setAttribute("errors", errors);
            request.setAttribute("userDTO", userDTO);

            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        } else if (userService.isNotUniqueEmail(userDTO)) {
            LOGGER.info("Email: {} is already exists", userDTO.getEmail());

            errors.put("email", "already exists, please use a different one");

            request.setAttribute("errors", errors);
            request.setAttribute("userDTO", userDTO);

            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        } else {
            LOGGER.info("User is valid, creating a new user with: {}", userDTO);
            userService.saveUser(userDTO);
            response.sendRedirect("/login");
        }
    }

    private UserDTO copyParametersTo(HttpServletRequest request) {
        var userDTO = new UserDTO();

        userDTO.setFirstName(request.getParameter("firstName"));
        userDTO.setLastName(request.getParameter("lastName"));
        userDTO.setPassword(request.getParameter("password"));
        userDTO.setPasswordConfirmed(request.getParameter("passwordConfirmed"));
        userDTO.setEmail(request.getParameter("email"));
        userDTO.setUsername(request.getParameter("username"));

        return userDTO;
    }

    private boolean isValid(UserDTO userDTO) {

        var validator = Validation.buildDefaultValidatorFactory().getValidator();

        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);

        return violations.size() == 0;
    }
}
