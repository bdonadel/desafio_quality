package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@AllArgsConstructor
public class Property {

    @NotBlank(message = "O id da propriedade não pode ser vazio")
    @Min(value = 1, message = "O id da propriedade não pode ser menor que zero (0)")
    private long propId;

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "[A-Z]+", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String propName;

    @NotEmpty(message = "O bairro deve ser informado.")
    private @Valid District propDistrict;

    @NotEmpty(message = "A propriedade deve ter ao menos 1 cômodo.")
    private List<@Valid Room> propRooms;

}

// TODO ver com Joice sobre validação da lista
// TODO testar validations apos modificacoes de endpoint + verificar exceptions
