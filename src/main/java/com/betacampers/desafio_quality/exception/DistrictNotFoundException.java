package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class DistrictNotFoundException extends CustomException {

    public DistrictNotFoundException(String districtName) {
        super("O bairro " + districtName + " não está registrado.", HttpStatus.NOT_FOUND);
    }
}
