package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.exceptions.*;
import com.example.sistema_chamado.exceptions.infra.StandardMessageError;
import com.example.sistema_chamado.models.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionGlobalController extends RuntimeException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ApiError apiError = new ApiError("Um ou mais campos estão inválidos", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    @ExceptionHandler(CustomerNotFoundById.class)
    private ResponseEntity<StandardMessageError> customerNotFoundById (CustomerNotFoundById customerNotFoundById){
        StandardMessageError messageError = new StandardMessageError(404, customerNotFoundById.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(CustomerNotFoundByName.class)
    private ResponseEntity<String> customerNotFoundByName (CustomerNotFoundByName customerNotFoundByName){
        String data = customerNotFoundByName.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(CustomerPasswordNotExists.class)
    private ResponseEntity<String> customerPasswordNotExists (CustomerPasswordNotExists customerPasswordNotExists){
        String data = customerPasswordNotExists.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(TechnicalNotFoundByName.class)
    private ResponseEntity<String> technicalNotFoundByName (TechnicalNotFoundByName technicalNotFoundByName){
        String data = technicalNotFoundByName.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(TechnicalNotFoundInList.class)
    private ResponseEntity<String> technicalNotFoundOnList (TechnicalNotFoundInList technicalNotFoundInList){
        String data = technicalNotFoundInList.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(CalledNotFoundById.class)
    private ResponseEntity<String> calledNotFoundById (CalledNotFoundById calledNotFoundById){
        String data = calledNotFoundById.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<StandardMessageError> dataAlreadyExists () {
        StandardMessageError messageError = new StandardMessageError(409, "Email ou telefone" +
                " já cadastrados");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<StandardMessageError> argumentMismatch () {
        StandardMessageError messageError = new StandardMessageError(400, "Argumento passado como " +
                "ID é inválido");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageError);
    }

}
