package com.survey.server.adapters.web.exception;

import com.survey.server.adapters.web.dto.ResponseDTO;
import com.survey.server.domain.exception.SurveyNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ControllerAdvice
public class HandlerGlobalException {

    @ExceptionHandler(SurveyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseDTO> handleSurveyNotFoundException(SurveyNotFoundException ex) {
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .message(ex.getMessage())
                        .timestamp(String.valueOf(System.currentTimeMillis()))
                        .status(HttpStatus.NOT_FOUND.value())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .data(errors)
                        .message("Campos inválidos")
                        .timestamp(String.valueOf(System.currentTimeMillis()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseDTO> handleConstraintViolationExceptions(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .data(errors)
                        .message("Campos inválidos")
                        .timestamp(String.valueOf(System.currentTimeMillis()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ResponseDTO> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach((error) -> {
            System.out.println("Error => " + error);
            String message = error.getDefaultMessage();
            errors.put("message", message);
        });

        return new ResponseEntity<>(
                ResponseDTO.builder()
                        .id(UUID.randomUUID().toString())
                        .data(errors)
                        .message("Campos inválidos")
                        .timestamp(String.valueOf(System.currentTimeMillis()))
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

}
