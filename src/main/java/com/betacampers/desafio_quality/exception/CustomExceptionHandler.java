package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(PropertyWithoutRoomException.class)
    public ResponseEntity<PropertyWithoutRoomException> propertyWithoutRoomHandler(PropertyWithoutRoomException ex) {
        return new ResponseEntity(ex.getError(), ex.getStatus());
    }
}