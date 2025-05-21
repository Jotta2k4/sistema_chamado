package com.example.sistema_chamado.exceptions;

public class CallAlreadyCompleted extends RuntimeException {
    public CallAlreadyCompleted(String message) {
        super(message);
    }
}
