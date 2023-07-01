package com.happypaws.domain;

public enum Animal {
    DOG("DOG"),
    CAT("CAT");

    private String label;

    private Animal(String label) {
        this.label = label;
    }
}
