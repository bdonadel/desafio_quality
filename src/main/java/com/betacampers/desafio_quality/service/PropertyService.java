package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.exception.PropertyNotFoundException;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service da Property, implementa Interface IPropertyService
 */
@Service
public class PropertyService implements IPropertyService {
    @Autowired
    private IPropertyRepository propertyRepository;

    @Autowired
    private IDistrictService districtService;

    @Override
    public Property saveProperty(PropertyRequestDto propertyRequest) {
        District district = districtService.getById(propertyRequest.getDistrictId());
        Property property = propertyRequest.toProperty(district);
        property.setPropDistrict(district);
        return propertyRepository.save(property);
    }

    @Override
    public Double getPropertyArea(Long propertyId) {
        return getById(propertyId).getPropArea();
    }

    @Override
    public BigDecimal getPropertyValue(Long propertyId) {
        return getById(propertyId).getPropValue();
    }

    @Override
    public RoomResponseDto getLargestRoom(Long propertyId) {
        return new RoomResponseDto(getById(propertyId).getLargestRoom());
    }

    @Override
    public List<RoomResponseDto> getRoomsArea(Long propertyId) {
        return getById(propertyId).getPropRooms().stream().map(RoomResponseDto::new).collect(Collectors.toList());
    }

    private Property getById(Long propertyId) {
        Property property = propertyRepository.getById(propertyId);

        if (property == null) {
            throw new PropertyNotFoundException(propertyId);
        }

        return property;
    }
}
