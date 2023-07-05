package com.happypaws.repositories;

import com.happypaws.domain.Appointment;
import com.happypaws.domain.OrderLineItem;
import com.happypaws.domain.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAll();
    Product findProductById(Long id);
    Appointment save(Appointment appointment);
    @Transactional
    @Modifying
    @Query("delete from Product p where p.id = ?1")
    void deleteById(Long id);
}
