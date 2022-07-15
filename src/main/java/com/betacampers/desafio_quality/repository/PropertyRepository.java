package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.PropertyNotFoundException;
import com.betacampers.desafio_quality.model.Property;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;

@Repository
public class PropertyRepository implements IPropertyRepository {

    private static HashMap<Long, Property> properties;
    private String SCOPE;

    public PropertyRepository() {
        Properties props = new Properties();

        try {
            props.load(new ClassPathResource("application.properties").getInputStream());
            this.SCOPE = props.getProperty("api.scope");
            this.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Property save(Property property) {
        Long greaterId = (properties.size() > 0) ? Collections.max(properties.keySet()) : 0L;
        property.setPropId(greaterId + 1L);
        properties.put(property.getPropId(), property);
        this.saveData();
        return property;
    }

    @Override
    public Property getById(Long propertyId) {
        if (!properties.containsKey(propertyId)) {
            throw new PropertyNotFoundException(propertyId);
        }
        return properties.get(propertyId);
    }

    private void loadData() {
        HashMap<Long, Property> loadedData = null;

        ObjectMapper objectMapper = new ObjectMapper();
        File file;
        try {
            file = ResourceUtils.getFile("./src/" + SCOPE + "/resources/property.json");
            loadedData = objectMapper.readValue(file, new TypeReference<HashMap<Long, Property>>() {
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your resources files");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your JSON formatting.");
        }

        properties = loadedData;
    }

    private void saveData() {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            File file = ResourceUtils.getFile("./src/" + SCOPE + "/resources/property.json");
            objectMapper.writeValue(file, properties);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed while writing to DB, check your resources files");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed while writing to DB, check your JSON formatting.");
        }
    }
}
