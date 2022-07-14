package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictRepository {
    District getById(long districtId);
    District save(District district);
}
