package com.happypaws.services;

import com.happypaws.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    void save(Product product);
    Product findProductById(Long id);
}
