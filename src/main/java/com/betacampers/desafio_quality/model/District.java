package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Representa um bairro
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class District {
    @Null(message = "O ID do bairro não pode estar presente.")
    private Long districtId;

    @NotBlank(message = "O nome do bairro não pode estar vazio.")
    @Pattern(regexp = "[A-Z].+", message = "O nome do bairro deve começar com uma letra maiúscula.")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder {max} caracteres.")
    private String districtName;

    @NotNull(message = "O valor do metro quadrado no bairro não pode estar vazio.")
    @Digits(integer = 13, fraction = 2, message = "O valor máximo permitido é {integer} dígitos e {fraction} casas decimais.")
    private BigDecimal valueDistrictM2;

    public District(String districtName, BigDecimal valueDistrictM2) {
        this.districtName = districtName;
        this.valueDistrictM2 = valueDistrictM2;
    }
}
