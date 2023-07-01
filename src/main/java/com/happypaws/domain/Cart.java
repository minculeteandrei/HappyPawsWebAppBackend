package com.happypaws.domain;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private User user;
    private List<OrderLineItem> products;

    public Cart(){
        this.products = new ArrayList<>();
    }

    public void add(OrderLineItem p){
        this.products.add(p);
    }

    public List<OrderLineItem> findAll(){return products;}
}

