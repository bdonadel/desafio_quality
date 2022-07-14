package com.betacampers.desafio_quality.exception;

import com.betacampers.desafio_quality.model.CustomError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomError> propertyWithoutRoomHandler(CustomException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getError());
    }

}
