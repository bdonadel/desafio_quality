package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;

import java.math.BigDecimal;
import java.util.List;

public interface IPropertyService {
    Property saveProperty(PropertyRequestDto property);
    Double getPropertyArea(long propertyId);
    BigDecimal getPropertyValue(long propertyId);
    RoomResponseDto getLargestRoom(long propertyId);
    List<RoomResponseDto> getRoomsArea(long propertyId);
    List<Property> getAllProperties();
}
