package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Property {
    private long propId;
    private String propName;
    private District propDistrict;
    private List<Room> propRooms;
}
