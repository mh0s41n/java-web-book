package com.mhosain.cart.repository;

import com.mhosain.cart.domain.ShippingAddress;

public interface ShippingAddressRepository {
    ShippingAddress save(ShippingAddress shippingAddress);
}
