package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DistrictRepositoryTest {

    private IDistrictRepository districtRepository;

    @BeforeEach
    void setup() {
        TestUtilsGenerator.emptyUsersFile();
        districtRepository = new DistrictRepository();
    }

    @Test
    void getById_returnDistrict_whenDistrictExists() {
        District district = TestUtilsGenerator.getNewDistrict();
        District savedDistrict = districtRepository.save(district);

        District foundDistrict = districtRepository.getById(savedDistrict.getDistrictId());

        assertThat(foundDistrict).isNotNull();
        assertThat(foundDistrict.getDistrictId()).isEqualTo(savedDistrict.getDistrictId());
        assertThat(foundDistrict.getDistrictName()).isEqualTo(savedDistrict.getDistrictName());
    }

    @Test
    void getById_throwException_whenDistrictNotExist() {
        District district = TestUtilsGenerator.getNewDistrictWithId();

        DistrictNotFoundException exception = Assertions.assertThrows(DistrictNotFoundException.class, () -> {
            districtRepository.getById(district.getDistrictId());
        });

        assertThat(exception.getError().getDescription()).contains(district.getDistrictId().toString());
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void save_returnDistrict_whenNewDistrict() {
        District district = TestUtilsGenerator.getNewDistrict();

        District savedDistrict = districtRepository.save(district);

        assertThat(savedDistrict).isNotNull();
        assertThat(savedDistrict.getDistrictId()).isPositive();
        assertThat(savedDistrict.getDistrictName()).isEqualTo(district.getDistrictName());
    }

    @Test
    void save_updateDistrict_whenDistrictWithId() {
        District district = TestUtilsGenerator.getNewDistrict();

        District savedDistrict = districtRepository.save(district);

        savedDistrict.setDistrictName("Novo nome");
        savedDistrict.setValueDistrictM2(new BigDecimal(233.0));

        District updatedDistrict = districtRepository.save(savedDistrict);

        assertThat(updatedDistrict).isNotNull();
        assertThat(updatedDistrict.getDistrictId()).isEqualTo(savedDistrict.getDistrictId());
        assertThat(updatedDistrict.getDistrictName()).isEqualTo(savedDistrict.getDistrictName());
    }

    @Test
    void save_throwException_whenDistrictIdExistsAndDistrictNotExist() {
        District district = TestUtilsGenerator.getNewDistrictWithId();

        DistrictNotFoundException exception = Assertions.assertThrows(DistrictNotFoundException.class, () -> {
            districtRepository.save(district);
        });

        assertThat(exception.getError().getDescription()).contains(district.getDistrictId().toString());
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
