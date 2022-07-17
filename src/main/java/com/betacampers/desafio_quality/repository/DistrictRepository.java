package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * Repository do District, implementa Interface IDistrictRepository
 */
@Repository
public class DistrictRepository implements IDistrictRepository {

    private final String scope;
    private Map<Long, District> districts;

    public DistrictRepository(String propertiesFile) {
        try {
            Properties properties = new Properties();
            properties.load(new ClassPathResource(propertiesFile).getInputStream());
            scope = properties.getProperty("api.scope");
            loadData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public DistrictRepository() {
        this("application.properties");
    }

    @Override
    public District getById(Long districtId) {
        loadData();
        return districts.get(districtId);
    }

    @Override
    public District save(District district) {
        if (!exists(district)) {
            Long greaterId = (districts.size() > 0) ? Collections.max(districts.keySet()) : 0L;
            district.setDistrictId(greaterId + 1L);
        }

        districts.put(district.getDistrictId(), district);
        saveData();

        return district;
    }

    public boolean exists(District district) {
        return getById(district.getDistrictId()) != null;
    }

    private void loadData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = ResourceUtils.getFile("./src/" + scope + "/resources/district.json");
            districts = mapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            throw new RuntimeException("Failed while initializing DB.", e);
        }
    }

    private void saveData() {
        try {
            ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            File file = ResourceUtils.getFile("./src/" + scope + "/resources/district.json");
            mapper.writeValue(file, districts);
        } catch (IOException e) {
            throw new RuntimeException("Failed while writing to DB.", e);
        }
    }
}
