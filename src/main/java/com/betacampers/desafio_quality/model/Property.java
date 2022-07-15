package com.betacampers.desafio_quality.model;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {

    private Long propId;
    private String propName;
    private District propDistrict;
    private List<Room> propRooms;

    public Property(PropertyRequestDto propertyRequest, District district) {
        propName = propertyRequest.getPropName();
        propDistrict = district;
        propRooms = propertyRequest.getPropRooms();
    }
}

// TODO ver com Joice sobre validação da lista
// TODO testar validations apos modificacoes de endpoint + verificar exceptions
