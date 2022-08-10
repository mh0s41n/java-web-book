package com.mhosain.cart.repository;

import com.mhosain.cart.domain.Cart;
import com.mhosain.cart.domain.User;

import java.util.Optional;

public interface CartRepository {
    Optional<Cart> findByUser(User currentUser);

    Cart save(Cart cart);

    Cart update(Cart cart);
}
