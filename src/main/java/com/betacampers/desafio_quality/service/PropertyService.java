package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService implements IPropertyService {
    @Autowired
    private IPropertyRepository repository;

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
        Property property = repository.getById(propertyId);
        return property.getPropRooms().stream().map(RoomResponseDto::new).collect(Collectors.toList());
    }
}
