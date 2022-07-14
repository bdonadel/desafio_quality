package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DistrictRepository implements IDistrictRepository {

    private static Map<Long, District> districts;

    public DistrictRepository() {
        districts = new ArrayList<District>() {{
            add(new District(1, "Centro", new BigDecimal("10")));
            add(new District(2, "Sossego", new BigDecimal("10")));
            add(new District(3, "Passo D'areia", new BigDecimal("10")));
        }}.stream().collect(Collectors.toMap(District::getDistrictId, x -> x));
    }

    @Override
    public District getById(long districtId) {
        return districts.get(districtId);
    }

    @Override
    public District getByName(String districtName) { //TODO
        /*districts.forEach(district -> {
            if(district.getName() == districtName) {
                return district;
            }
        });*/
        throw new DistrictNotFoundException(districtName);
    }
}
