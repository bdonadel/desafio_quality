package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception de imóvel sem cômodos cadastrados (para funções que precisam de cômodos)
 */
public class PropertyWithoutRoomException extends CustomException {
    public PropertyWithoutRoomException(long id) {
        super("Imóvel sem cômodos", "A propriedade " + id + " não possui cômodos registrados!", HttpStatus.BAD_REQUEST);
    }
}
