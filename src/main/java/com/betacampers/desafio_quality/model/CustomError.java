package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um erro para ser retornado em Exception
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomError {
    private String name;
    private String description;
}
