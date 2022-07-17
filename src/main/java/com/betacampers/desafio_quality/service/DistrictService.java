package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service do District, implementa Interface IDistrictService
 */
@Service
public class DistrictService implements IDistrictService {

    @Autowired
    private IDistrictRepository repository;

    @Override
    public District save(District district) {
        if (district.getDistrictId() != null && !repository.exists(district)) {
            throw new DistrictNotFoundException(district.getDistrictId());
        }
        return repository.save(district);
    }

    @Override
    public District getById(Long districtId) {
        District district = repository.getById(districtId);
        if (district == null) {
            throw new DistrictNotFoundException(districtId);
        }
        return district;
    }
}
