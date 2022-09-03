package com.mhosain.cart.service;

import com.mhosain.cart.domain.User;
import com.mhosain.cart.dto.ShippingAddressDTO;

public interface OrderService {
    void processOrder(ShippingAddressDTO shippingAddressDTO, User currentUser);
}
