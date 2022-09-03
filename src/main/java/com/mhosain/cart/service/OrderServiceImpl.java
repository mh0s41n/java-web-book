package com.mhosain.cart.service;

import com.mhosain.cart.domain.Order;
import com.mhosain.cart.domain.ShippingAddress;
import com.mhosain.cart.domain.User;
import com.mhosain.cart.dto.ShippingAddressDTO;
import com.mhosain.cart.exceptions.CartNotFoundException;
import com.mhosain.cart.repository.CartRepository;
import com.mhosain.cart.repository.OrderRepository;
import com.mhosain.cart.repository.ShippingAddressRepository;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ShippingAddressRepository shippingAddressRepository;
    private CartRepository cartRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ShippingAddressRepository shippingAddressRepository, CartRepository cartRepository) {
        this.orderRepository = orderRepository;
        this.shippingAddressRepository = shippingAddressRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public void processOrder(ShippingAddressDTO shippingAddressDTO, User currentUser) {
        var shippingAddress = convertTo(shippingAddressDTO);
        var savedShippingAddress = shippingAddressRepository.save(shippingAddress);
        var cart = cartRepository.findByUser(currentUser)
                .orElseThrow(() -> new CartNotFoundException("Cart not found by current user"));

        var order = new Order();

        order.setCart(cart);
        order.setShippingAddress(savedShippingAddress);
        order.setShipped(false);
        order.setUser(currentUser);

        orderRepository.save(order);
    }

    private ShippingAddress convertTo(ShippingAddressDTO shippingAddressDTO) {
        var shippingAddress = new ShippingAddress();

        shippingAddress.setAddress(shippingAddressDTO.getAddress());
        shippingAddress.setAddress2(shippingAddressDTO.getAddress2());
        shippingAddress.setCountry(shippingAddressDTO.getCountry());
        shippingAddress.setState(shippingAddressDTO.getState());
        shippingAddress.setZip(shippingAddressDTO.getZip());
        shippingAddress.setMobileNumber(shippingAddressDTO.getMobileNumber());

        return shippingAddress;
    }
}
