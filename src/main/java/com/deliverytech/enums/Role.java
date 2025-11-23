package com.deliverytech.enums;

public enum Role {
    ADMIN("ADMIN"),
    RESTAURANTE("RESTAURANTE"),
    CLIENTE("CLIENTE");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}
