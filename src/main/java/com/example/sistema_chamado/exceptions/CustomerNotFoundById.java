package com.example.sistema_chamado.exceptions;

public class CustomerNotFoundById extends RuntimeException{
    public CustomerNotFoundById(String message) {
        super(message);
    }
}
