package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PropertyRepository implements IPropertyRepository {
    @Override
    public Property getById(UUID propertyId) {
        return null;
    }
}
