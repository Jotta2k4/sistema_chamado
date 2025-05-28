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
    private ResponseEntity<StandardMessageError> customerNotFoundByName (CustomerNotFoundByName customerNotFoundByName){
        StandardMessageError messageError = new StandardMessageError(404, customerNotFoundByName.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(CustomerPasswordNotExists.class)
    private ResponseEntity<StandardMessageError> customerPasswordNotExists (CustomerPasswordNotExists customerPasswordNotExists){
        StandardMessageError messageError = new StandardMessageError(404, customerPasswordNotExists.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(TechnicalNotFoundByName.class)
    private ResponseEntity<StandardMessageError> technicalNotFoundByName (TechnicalNotFoundByName technicalNotFoundByName){
        StandardMessageError messageError = new StandardMessageError(404, technicalNotFoundByName.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(TechnicalNotFoundInList.class)
    private ResponseEntity<StandardMessageError> technicalNotFoundOnList (TechnicalNotFoundInList technicalNotFoundInList){
        StandardMessageError messageError = new StandardMessageError(404, technicalNotFoundInList.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(NoTechnicalRegistered.class)
    private ResponseEntity<StandardMessageError> noTechnicalRegistered (NoTechnicalRegistered noTechnicalRegistered){
        StandardMessageError messageError = new StandardMessageError(404, noTechnicalRegistered.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(NoTechnicalAvailable.class)
    private ResponseEntity<StandardMessageError> noTechnicalAvailable (NoTechnicalAvailable noTechnicalAvailable){
        StandardMessageError messageError = new StandardMessageError(404, noTechnicalAvailable.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(CalledNotFoundById.class)
    private ResponseEntity<StandardMessageError> calledNotFoundById (CalledNotFoundById calledNotFoundById){
        StandardMessageError messageError = new StandardMessageError(404, calledNotFoundById.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(CallAlreadyCompleted.class)
    private ResponseEntity<StandardMessageError> callAlreadyCompleted (CallAlreadyCompleted callAlreadyCompleted){
        StandardMessageError messageError = new StandardMessageError(404, callAlreadyCompleted.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(CallCanceled.class)
    private ResponseEntity<StandardMessageError> callCanceled (CallCanceled callCanceled){
        StandardMessageError messageError = new StandardMessageError(404, callCanceled.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<StandardMessageError> dataAlreadyExists () {
        StandardMessageError messageError = new StandardMessageError(400, "Email ou telefone" +
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
