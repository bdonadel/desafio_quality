package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PropertyRepository implements IPropertyRepository {
    private static long nextId = 1;

    private final Map<Long, Property> properties = new HashMap<>();

    public void addProperty(Property property) {
        property.setPropId(nextId++);
        properties.put(property.getPropId(), property);
    }

    @Override
    public Property getById(long propertyId) {
        if (!properties.containsKey(propertyId)){
            throw new RuntimeException("Não existe");
        }
        return properties.get(propertyId);
    }

    @Override
    public List<Property> getAll() {
        return new ArrayList<>(properties.values());
    }

    @Override
    public void clear() {
        properties.clear();
    }
}
