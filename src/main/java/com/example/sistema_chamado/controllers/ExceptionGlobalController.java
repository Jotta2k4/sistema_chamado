package com.example.sistema_chamado.controllers;

import com.example.sistema_chamado.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionGlobalController extends RuntimeException{

    @ExceptionHandler(CustomerNotFoundById.class)
    public ResponseEntity<String> customerNotFoundById (CustomerNotFoundById customerNotFoundById){
        String data = customerNotFoundById.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(CustomerNotFoundByName.class)
    public ResponseEntity<String> customerNotFoundByName (CustomerNotFoundByName customerNotFoundByName){
        String data = customerNotFoundByName.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(CustomerPasswordNotExists.class)
    public ResponseEntity<String> customerPasswordNotExists (CustomerPasswordNotExists customerPasswordNotExists){
        String data = customerPasswordNotExists.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(TechnicalNotFoundByName.class)
    public ResponseEntity<String> technicalNotFoundByName (TechnicalNotFoundByName technicalNotFoundByName){
        String data = technicalNotFoundByName.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(TechnicalNotFoundInList.class)
    public ResponseEntity<String> technicalNotFoundOnList (TechnicalNotFoundInList technicalNotFoundInList){
        String data = technicalNotFoundInList.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

    @ExceptionHandler(CalledNotFoundById.class)
    public ResponseEntity<String> calledNotFoundById (CalledNotFoundById calledNotFoundById){
        String data = calledNotFoundById.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(data);
    }

}
