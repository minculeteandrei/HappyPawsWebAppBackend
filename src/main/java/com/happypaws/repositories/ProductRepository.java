package com.happypaws.repositories;

import com.happypaws.domain.Appointment;
import com.happypaws.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    Product findProductById(Long id);
    Appointment save(Appointment appointment);
}
