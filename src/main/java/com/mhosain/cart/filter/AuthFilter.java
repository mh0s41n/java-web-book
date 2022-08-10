package com.mhosain.cart.filter;

import com.mhosain.cart.util.SecurityContext;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.stream.Stream;

@WebFilter(urlPatterns = "/*")
public class AuthFilter implements Filter {
    public static final String[] ALLOWED_CONTENTS = {".css", ".js", ".jpg", ".png", "home", "login", "signup"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httServletRequest = (HttpServletRequest) request;
        var requestedUri = httServletRequest.getRequestURI();

        boolean allowed = Stream.of(ALLOWED_CONTENTS).anyMatch(requestedUri::contains);

        if (requestedUri.equals("/")
                || allowed
                || SecurityContext.isAuthenticated(httServletRequest)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendRedirect("/login");
        }
    }
}
