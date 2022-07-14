package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistrictService implements IDistrictService {

    @Autowired
    private IDistrictRepository repository;

    @Override
    public District save(District district) {
        return repository.save(district);
    }

    @Override
    public District getById(Long districtId) {
        return repository.getById(districtId);
    }
}
