package com.mhosain.cart.repository;

import com.mhosain.cart.domain.CartItem;

public interface CartItemRepository {
    CartItem save(CartItem cartItem);

    CartItem update(CartItem cartItem);
}
