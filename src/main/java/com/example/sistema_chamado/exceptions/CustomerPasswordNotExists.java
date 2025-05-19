package com.example.sistema_chamado.exceptions;

public class CustomerPasswordNotExists extends RuntimeException {
    public CustomerPasswordNotExists(String message) {
        super(message);
    }
}
