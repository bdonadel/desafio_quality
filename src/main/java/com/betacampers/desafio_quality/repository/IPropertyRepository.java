package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;

import java.util.List;
import java.util.UUID;

public interface IPropertyRepository {
    Property getById(UUID propertyId);
    List<Property> getAll();
}
