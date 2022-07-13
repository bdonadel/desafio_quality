package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class DistrictRepository implements IDistrictRepository {
    @Override
    public District getById(long districtId) {
        return null;
    }
}
