package com.example.sistema_chamado.exceptions;

public class CalledNotFoundById extends RuntimeException {
    public CalledNotFoundById(String message) {
        super(message);
    }
}
