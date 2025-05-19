package com.example.sistema_chamado.models;

import jakarta.persistence.Entity;
@Entity

public class Customer extends User {
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
