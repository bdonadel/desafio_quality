package com.betacampers.desafio_quality.model;

import com.betacampers.desafio_quality.exception.PropertyWithoutRoomException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

/**
 * Representa um imóvel
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    private Long propId;
    private String propName;
    private District propDistrict;
    private List<Room> propRooms;

    public Property(String propName, District propDistrict, List<Room> propRooms) {
        setPropName(propName);
        setPropDistrict(propDistrict);
        setPropRooms(propRooms);
    }

    public List<Room> getPropRooms() {
        if (propRooms == null || propRooms.isEmpty()) {
            throw new PropertyWithoutRoomException(propId);
        }
        return propRooms;
    }

    @JsonIgnore
    public BigDecimal getPropValue() {
        return getPropDistrict()
                .getValueDistrictM2()
                .multiply(BigDecimal.valueOf(getPropArea()))
                .setScale(2, RoundingMode.CEILING);
    }

    @JsonIgnore
    public double getPropArea() {
        return getPropRooms()
                .stream()
                .mapToDouble(Room::getRoomArea)
                .sum();
    }

    @JsonIgnore
    public Room getLargestRoom() {
        return getPropRooms()
                .stream()
                .max(Comparator.comparing(Room::getRoomArea))
                .get();
    }
}
