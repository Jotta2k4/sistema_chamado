package com.example.sistema_chamado.exceptions;

public class CallCanceled extends RuntimeException {
    public CallCanceled(String message) {
        super(message);
    }
}
