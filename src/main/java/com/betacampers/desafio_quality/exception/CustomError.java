package com.betacampers.desafio_quality.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Representa um erro para ser retornado em Exception
 */
@Getter
@AllArgsConstructor
public class CustomError {
    private String name;
    private String description;
}
