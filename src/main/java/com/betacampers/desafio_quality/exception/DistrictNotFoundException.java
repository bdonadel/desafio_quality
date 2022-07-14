package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class DistrictNotFoundException extends CustomException {

    public DistrictNotFoundException(Long id) {
        super("O bairro com Id " + id + " não está registrado.", HttpStatus.NOT_FOUND);
    }
}
