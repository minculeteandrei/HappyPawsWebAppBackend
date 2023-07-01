package com.happypaws.services;

import com.happypaws.domain.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    Product save(Product product);
    Product findProductById(Long id);
    Product findById(Long id);
    void deleteById(Long id);
}
