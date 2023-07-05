package com.happypaws.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    private double price;
    private String image;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product", orphanRemoval = true)
    private List<OrderLineItem> orderLineItems;

    public Product(String name, String description, double price, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }
}
