package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

/**
 * DTO de requisição para cadastrar imóvel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDto {

    @NotBlank(message = "O nome da propriedade não pode estar vazio.")
    @Pattern(regexp = "[A-Z].+", message = "O nome da propriedade deve começar com uma letra maiúscula.")
    @Size(max = 30, message = "O comprimento do nome não pode exceder 30 caracteres.")
    private String propName;

    @NotNull(message = "O bairro deve ser informado.")
    private Long districtId;

    @NotEmpty(message = "A propriedade deve ter ao menos 1 cômodo.")
    private List<@Valid Room> propRooms;
}
