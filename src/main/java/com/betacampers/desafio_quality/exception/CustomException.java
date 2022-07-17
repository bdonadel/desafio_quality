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

    public CustomException(String name, String message, HttpStatus status) {
        error = new CustomError(name, message);
        this.status = status;
    }
}
