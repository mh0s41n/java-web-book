package com.mhosain.cart.exceptions;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
    }

    public CartNotFoundException(String message) {
        super(message);
    }
}
