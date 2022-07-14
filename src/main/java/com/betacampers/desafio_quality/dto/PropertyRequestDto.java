package com.betacampers.desafio_quality.dto;

import com.betacampers.desafio_quality.model.Room;
import lombok.Data;

import java.util.List;

@Data
public class PropertyRequestDto {
    private String propName;
    private String districtName;
    private List<Room> propRooms;
}
