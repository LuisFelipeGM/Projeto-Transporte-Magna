package br.com.magna.trainees.transporte.controllers;

import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.magna.trainees.transporte.dtos.ErroDto;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class GenericController {

    List<String> getErrors(@NotNull BindingResult result) {
        return result.getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError) {
                        FieldError fieldError = (FieldError) error;
                        return fieldError.getField().toUpperCase(Locale.ROOT) + " : " + error.getDefaultMessage();
                    } else {
                        return error.getDefaultMessage();
                    }
                })
                .collect(Collectors.toList());
    }

    ResponseEntity<Object> handleErrors(@NotNull Exception e) {
        ErroDto erroDTO = new ErroDto("Ocorreu um erro: ", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erroDTO);
    }

}
