package com.example.sistema_chamado.exceptions;

public class CustomerNotFoundByName extends RuntimeException {
    public CustomerNotFoundByName(String message) {
        super(message);
    }
}
