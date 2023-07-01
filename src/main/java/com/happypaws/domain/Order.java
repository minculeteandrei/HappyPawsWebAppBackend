package com.happypaws.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "order_table")
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<OrderLineItem> items;
    private Long totalPrice;
    @ManyToOne(cascade = CascadeType.MERGE)
    private User user;

    public Order(Set<OrderLineItem> items, User user) {
        this.items = items;
        this.user = user;

        long sum = 0;
        for(OrderLineItem o : items)
            sum += o.getProduct().getPrice() * o.getQuantity();

        this.totalPrice = sum;
    }

    public Set<OrderLineItem> getItems() {
        return items;
    }
}
