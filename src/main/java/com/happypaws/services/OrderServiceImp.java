package com.happypaws.services;

import com.happypaws.domain.Order;
import com.happypaws.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImp implements OrderService{
    private final OrderRepository orderRepository;

    public OrderServiceImp(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }
}
