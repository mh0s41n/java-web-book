package com.mhosain.cart.repository;

import com.mhosain.cart.domain.Order;
import com.mhosain.cart.domain.User;

import java.util.Set;

public interface OrderRepository {
    Order save(Order order);

    Set<Order> findOrderByUser(User currentUser);
}
