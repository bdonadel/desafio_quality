package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Room;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IPropertyService {
    Double getPropertyArea(UUID propertyId);
    BigDecimal getPropertyValue(UUID propertyId);
    RoomResponseDto getLargestRoom(UUID propertyId);
    List<RoomResponseDto> getRoomsArea(UUID propertyId);
}
