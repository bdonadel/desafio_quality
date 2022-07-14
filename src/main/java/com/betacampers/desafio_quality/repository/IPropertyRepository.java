package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.model.Property;

import java.util.List;

public interface IPropertyRepository {
    Property save(PropertyRequestDto property);
    Property getById(long propertyId);
    List<Property> getAll();
    void clear();
}
