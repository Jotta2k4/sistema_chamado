package com.example.sistema_chamado.exceptions.infra;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class StandardMessageError {
    private final int httpStatus;
    private final String message;
    private final LocalDateTime dateTime;

    public StandardMessageError(int httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        dateTime = LocalDateTime.now();
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
