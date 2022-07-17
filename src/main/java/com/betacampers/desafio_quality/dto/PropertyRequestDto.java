package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * DTO de requisição para cadastrar imóvel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDto {

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "[A-Z].+", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder {max} caracteres.")
    private String propName;

    @NotNull(message = "O bairro deve ser informado.")
    private Long districtId;

    @NotEmpty(message = "A propriedade deve ter ao menos 1 cômodo.")
    private List<@Valid Room> propRooms;

    public Property toProperty(District district) {
        return new Property(getPropName(), district, getPropRooms());
    }
}
