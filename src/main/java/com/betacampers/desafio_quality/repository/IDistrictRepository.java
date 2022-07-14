package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictRepository {
    District getByName(String districtName);
    District getById(long districtId);
}
