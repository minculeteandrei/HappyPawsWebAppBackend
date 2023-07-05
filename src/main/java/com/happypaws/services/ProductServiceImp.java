package com.happypaws.services;

import com.happypaws.domain.OrderLineItem;
import com.happypaws.domain.Product;
import com.happypaws.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product save(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public Product findProductById(Long id) {
        return productRepository.findProductById(id);
    }

    @Override
    public Product findById(Long id) {
        return this.productRepository.findProductById(id);
    }

    @Override
    public void deleteById(Long id) {
        Product product = this.productRepository.findProductById(id);
        for(OrderLineItem item : product.getOrderLineItems()) {
            item.setProduct(null);
        }
        this.productRepository.deleteById(id);
    }
}
