package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PropertyService implements IPropertyService {
    @Override
    public Double getPropertyArea(UUID propertyId) {
        return null;
    }

    @Override
    public BigDecimal getPropertyValue(UUID propertyId) {
        return null;
    }

    @Override
    public RoomResponseDto getLargestRoom(UUID propertyId) {
        return null;
    }

    @Override
    public List<RoomResponseDto> getRoomsArea(UUID propertyId) {
        return null;
    }
}
