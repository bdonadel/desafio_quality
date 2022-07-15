package com.betacampers.desafio_quality.model;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    @NotBlank(message = "O id da propriedade não pode ser vazio")
    @Min(value = 1, message = "O id da propriedade não pode ser menor que zero (0)")
    private Long propId;

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "[A-Z]+", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String propName;

    @NotEmpty(message = "O bairro deve ser informado.")
    private @Valid District propDistrict;

    @NotEmpty(message = "A propriedade deve ter ao menos 1 cômodo.")
    private List<@Valid Room> propRooms;

    public Property(PropertyRequestDto propertyRequest, District district) {
        propName = propertyRequest.getPropName();
        propDistrict = district;
        propRooms = propertyRequest.getPropRooms();
    }
}

// TODO ver com Joice sobre validação da lista
// TODO testar validations apos modificacoes de endpoint + verificar exceptions
