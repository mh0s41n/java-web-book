package com.mhosain.cart.repository;

import com.mhosain.cart.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<Product> findAllProducts();

    Optional<Product> findById(Long productId);
}
