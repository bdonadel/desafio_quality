package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {

    @NotBlank(message = "O id do bairro não pode ser vazio")
    @Min(value = 1, message = "O id do bairro não pode ser menor que zero (0)")
    private Long districtId;

    @NotBlank(message = "O nome do bairro não pode estar vazio")
    @Size(max = 45, message = "O comprimento do bairro não pode exceder 45 caracteres")
    private String districtName;

    @NotBlank(message = "O valor do metro quadrado no bairro não pode estar vazio.")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal valueDistrictM2;

    public District(String districtName, BigDecimal valueDistrictM2){
        this.districtName = districtName;
        this.valueDistrictM2 = valueDistrictM2;
    }
}

// TODO testar validations apos modificacoes de endpoint + verificar exceptions
