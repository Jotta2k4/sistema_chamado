package com.example.sistema_chamado.exceptions;

public class NoTechnicalAvailable extends RuntimeException {
    public NoTechnicalAvailable(String message) {
        super(message);
    }
}
