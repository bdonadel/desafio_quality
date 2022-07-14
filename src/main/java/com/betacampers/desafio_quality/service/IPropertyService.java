package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;

import java.math.BigDecimal;
import java.util.List;

public interface IPropertyService {
    Property saveProperty(PropertyRequestDto property);
    Double getPropertyArea(Long propertyId);
    BigDecimal getPropertyValue(Long propertyId);
    RoomResponseDto getLargestRoom(Long propertyId);
    List<RoomResponseDto> getRoomsArea(Long propertyId);
    List<Property> getAllProperties();
}
