package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;

import java.util.UUID;

public interface IDistrictRepository {
    District getById(long districtId);
}
