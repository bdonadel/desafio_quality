package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

/**
 * Repository da Property, implementa Interface IPropertyRepository
 */
@Repository
public class PropertyRepository implements IPropertyRepository {

    private HashMap<Long, Property> properties;
    private final String scope;

    public PropertyRepository(String propertiesFile) {
        try {
            Properties props = new Properties();
            props.load(new ClassPathResource(propertiesFile).getInputStream());
            scope = props.getProperty("api.scope");
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PropertyRepository() {
        this("application.properties");
    }

    @Override
    public Property save(Property property) {
        Long greaterId = (properties.size() > 0) ? Collections.max(properties.keySet()) : 0L;
        property.setPropId(greaterId + 1L);
        properties.put(property.getPropId(), property);
        saveData();
        return property;
    }

    @Override
    public Property getById(Long propertyId) {
        return properties.get(propertyId);
    }

    private void loadData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = ResourceUtils.getFile("./src/" + scope + "/resources/property.json");
            properties = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed while initializing DB.", e);
        }
    }

    private void saveData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            File file = ResourceUtils.getFile("./src/" + scope + "/resources/property.json");
            objectMapper.writeValue(file, properties);
        } catch (IOException e) {
            throw new RuntimeException("Failed while writing to DB.", e);
        }
    }
}
