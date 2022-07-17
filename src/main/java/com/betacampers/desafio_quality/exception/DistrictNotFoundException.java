package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception de bairro não achado na base de dados
 */
public class DistrictNotFoundException extends CustomException {
    public DistrictNotFoundException(Long id) {
        super("Bairro não encontrado", "O bairro com Id " + id + " não está registrado.", HttpStatus.NOT_FOUND);
    }
}
