package com.example.sistema_chamado.exceptions;

public class TechnicalNotFoundByName extends RuntimeException {
    public TechnicalNotFoundByName(String message) {
        super(message);
    }
}
