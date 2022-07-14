package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.District;

public interface IDistrictService {
    District save(District district);
    District getById(Long districtId);
}
