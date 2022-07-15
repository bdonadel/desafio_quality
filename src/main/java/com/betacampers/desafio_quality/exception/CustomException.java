package com.betacampers.desafio_quality.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception genérica
 */
@Getter
public class CustomException extends RuntimeException {

    private final CustomError error;
    private final HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        error = new CustomError(getClass().getSimpleName(), message);
        this.status = status;
    }

}
