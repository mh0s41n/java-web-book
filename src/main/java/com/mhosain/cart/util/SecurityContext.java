package com.mhosain.cart.util;

import com.mhosain.cart.domain.User;
import jakarta.servlet.http.HttpServletRequest;

public class SecurityContext {
    public static final String AUTHENTICATION_KEY = "auth.Key";

    public static void login(HttpServletRequest request, User user) {
        var oldSession = request.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        var session = request.getSession(true);
        session.setAttribute(AUTHENTICATION_KEY, user);
    }

    public static void logout(HttpServletRequest request) {
        var session = request.getSession(true);
        session.removeAttribute(AUTHENTICATION_KEY);
    }

    public static User getCurrentUser(HttpServletRequest request) {
        var session = request.getSession(true);

        return (User) session.getAttribute(AUTHENTICATION_KEY);
    }

    public static boolean isAuthenticated(HttpServletRequest request) {
        var session = request.getSession(true);

        return session.getAttribute(AUTHENTICATION_KEY) != null;
    }
}
