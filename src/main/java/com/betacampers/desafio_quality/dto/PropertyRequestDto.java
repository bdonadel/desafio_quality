package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO de requisição para cadastrar imóvel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequestDto {
    private String propName;
    private Long districtId;
    private List<Room> propRooms;
}
