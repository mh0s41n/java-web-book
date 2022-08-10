package com.mhosain.cart.service;

import com.mhosain.cart.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> findAllProductSortedByName();
}
