package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictRepository {
    District getById(Long districtId);
    District save(District district);
}
