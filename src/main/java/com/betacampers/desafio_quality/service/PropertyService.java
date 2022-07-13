package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyService implements IPropertyService {
    @Autowired
    private IPropertyRepository propertyRepository;

    @Override
    public Double getPropertyArea(long propertyId) {
        return null;
    }

    @Override
    public BigDecimal getPropertyValue(long propertyId) {
        Property property = propertyRepository.getById(propertyId);

        double m2 = 0;
        for(Room r:property.getPropRooms()){
            m2 += r.getRoomLength() * r.getRoomWidth();
        }

        return property.getPropDistrict().getValueDistrictM2().multiply(new BigDecimal(m2)).setScale(2, RoundingMode.CEILING);
    }

    @Override
    public RoomResponseDto getLargestRoom(long propertyId) {
        return null;
    }

    @Override
    public List<RoomResponseDto> getRoomsArea(long propertyId) {
        Property property = propertyRepository.getById(propertyId);
        return property.getPropRooms().stream().map(RoomResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<Property> getAllProperties() {
        return propertyRepository.getAll();
    }
}
