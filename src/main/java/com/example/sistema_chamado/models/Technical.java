package com.example.sistema_chamado.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Technical extends User {
    @OneToMany(mappedBy = "technical") @JsonManagedReference
    private List<Called> called = new ArrayList<>();

    public List<Called> getCalled() {
        return called;
    }

    public void setCalled(List<Called> called) {
        this.called = called;
    }
}
