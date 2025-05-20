package com.example.sistema_chamado.models;

import java.util.Map;

public class ApiError {

    private Map<String, String> errors;
    private String title;

    public ApiError(String title, Map<String, String> errors) {
        this.errors = errors;
        this.title = title;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public String getTitle() {
        return title;
    }
}
