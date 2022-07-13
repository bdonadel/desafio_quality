package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;

@Repository
public class DistrictRepository implements IDistrictRepository {

    private static Map<Long, District> districts;

    private static void createDistricts() {
        District d1 = new District(1234L, "Barreiros", new BigDecimal(4500));
        District d2 = new District(4567L, "Campinas", new BigDecimal(5000));
        District d3 = new District(8890L, "Kobrasol", new BigDecimal(4900));

        districts.put(d1.getDistrictId(), d1);
        districts.put(d2.getDistrictId(), d2);
        districts.put(d3.getDistrictId(), d3);
    }

    public static Map<Long, District> getDistricts() {
        createDistricts();
        return DistrictRepository.districts;
    }

    @Override
    public District getById(Long districtId) {
        return null;
    }
}
