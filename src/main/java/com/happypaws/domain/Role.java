package com.happypaws.domain;

public enum Role {
    USER("user"),
    ADMIN("admin"),
    DOCTOR("doctor");
    private final String label;

    private Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
