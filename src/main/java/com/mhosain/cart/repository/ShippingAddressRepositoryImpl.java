package com.mhosain.cart.repository;

import com.mhosain.cart.domain.ShippingAddress;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class ShippingAddressRepositoryImpl implements ShippingAddressRepository {
    public static final Set<ShippingAddress> SHIPPING_ADDRESSES = new CopyOnWriteArraySet<>();

    @Override
    public ShippingAddress save(ShippingAddress shippingAddress) {
        SHIPPING_ADDRESSES.add(shippingAddress);

        return shippingAddress;
    }
}
