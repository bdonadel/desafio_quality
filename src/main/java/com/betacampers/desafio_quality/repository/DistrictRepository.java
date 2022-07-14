package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
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
import java.util.Map;
import java.util.Properties;

@Repository
public class DistrictRepository implements IDistrictRepository {

    private String SCOPE;

    private static Map<Long, District> districts;

    public DistrictRepository() {
        Properties properties = new Properties();

        try {
            properties.load(new ClassPathResource("application.properties").getInputStream());
            this.SCOPE = properties.getProperty("api.scope");
            this.loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public District getById(Long districtId) {
        loadData();
        District district = districts.get(districtId);
        if (district != null) {
            return district;
        }
        throw new DistrictNotFoundException(districtId);
    }

    @Override
    public District save(District district) {
        boolean districtNotExist = !exists(district);

        if (district.getDistrictId() != null && districtNotExist) {
            throw new DistrictNotFoundException(district.getDistrictId());
        }

        if (districtNotExist) {
            Long greaterId = (districts.size() > 0) ? Collections.max(districts.keySet()) : 0L;
            district.setDistrictId(greaterId + 1L);
        }

        districts.put(district.getDistrictId(), district);

        this.saveData();

        return district;
    }

    public boolean exists(District district) {
        boolean ret = false;
        try {
            ret = this.getById(district.getDistrictId()) != null;
        } catch (DistrictNotFoundException e) {
        }

        return ret;
    }

    private void loadData() {
        HashMap<Long, District> loadedData = null;

        ObjectMapper objectMapper = new ObjectMapper();
        File file;
        try {
            file = ResourceUtils.getFile("./src/" + SCOPE + "/resources/district.json");
            loadedData = objectMapper.readValue(file, new TypeReference<HashMap<Long, District>>() {
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your resources files");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed while initializing DB, check your JSON formatting.");
        }

        this.districts = loadedData;
    }

    private void saveData() {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        try {
            File file = ResourceUtils.getFile("./src/" + SCOPE + "/resources/district.json");
            objectMapper.writeValue(file, this.districts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Failed while writing to DB, check your resources files");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed while writing to DB, check your JSON formatting.");
        }
    }
}
