package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
public class Room {

    @NotBlank(message = "O campo não pode estar vazio")
    @Pattern(regexp = "[A-Z]+", message = "O nome do cômodo deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O nome do cômodo não pode exceder 30 caracteres")
    private String roomName;

    @NotBlank(message = "A largura do cômodo não pode estar vazia.")
    @DecimalMax(value = "25.0", message = "Largura máxima permitida é de 25 metros.")
    private double roomWidth;

    @NotBlank(message = "O comprimento do cômodo não pode estar vazio.")
    @DecimalMax(value = "33.0", message = "O comprimento máximo perimitido por cômodo é de 33 metros.")
    private double roomLength;

    // TODO testar validations apos modificacoes de endpoint + verificar exceptions

}
