package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception de imóvel não achado na base de dados
 */
public class PropertyNotFoundException extends CustomException {
    public PropertyNotFoundException(Long id) {
        super("Imóvel não encontrado", "A propriedade com Id " + id + " não está registrada.", HttpStatus.NOT_FOUND);
    }
}
