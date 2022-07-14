package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PropertyRequestDto {
    private String propName;
    private Long districtId;
    private List<Room> propRooms;
}
