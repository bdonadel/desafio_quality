package com.betacampers.desafio_quality.exception;

import org.springframework.http.HttpStatus;

public class PropertyWithoutRoomException extends CustomException {

    public PropertyWithoutRoomException(long id) {
        super("A propriedade " + id + " não possui cômodos registrados!", HttpStatus.BAD_REQUEST);
    }
}
