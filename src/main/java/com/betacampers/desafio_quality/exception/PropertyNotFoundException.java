package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class PropertyNotFoundException extends CustomException{

    public PropertyNotFoundException(Long id) {
        super("A propriedade com Id " + id + " não está registrada.", HttpStatus.NOT_FOUND);
    }
}
