package com.betacampers.desafio_quality.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * Representa um cômodo de um imóvel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @NotBlank(message = "O nome do cômodo não pode estar vazio.")
    @Pattern(regexp = "[A-Z].+", message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O nome do cômodo não pode exceder {max} caracteres.")
    private String roomName;

    @Positive(message = "A largura do cômodo não pode estar vazia.")
    @DecimalMax(value = "25.0", message = "A largura máxima permitida por cômodo é de {value} metros.")
    private double roomWidth;

    @Positive(message = "O comprimento do cômodo não pode estar vazio.")
    @DecimalMax(value = "33.0", message = "O comprimento máximo permitido por cômodo é de {value} metros.")
    private double roomLength;

    @JsonIgnore
    public double getRoomArea() {
        return roomWidth * roomLength;
    }
}
