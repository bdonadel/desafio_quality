package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DistrictRepositoryTest {

    private IDistrictRepository districtRepository;

    @BeforeEach
    void setup() {
        TestUtilsGenerator.emptyUsersFile();
        districtRepository = new DistrictRepository();
    }

    @Test
    void getById_returnDistrict_whenDistrictExists() {
        // Arrange
        District savedDistrict = districtRepository.save(TestUtilsGenerator.getNewDistrict());

        // Act
        District foundDistrict = districtRepository.getById(savedDistrict.getDistrictId());

        // Assert
        assertThat(foundDistrict).isNotNull();
        assertEquals(foundDistrict.getDistrictId(), savedDistrict.getDistrictId());
        assertEquals(foundDistrict.getDistrictName(), savedDistrict.getDistrictName());
        assertEquals(foundDistrict.getValueDistrictM2(), savedDistrict.getValueDistrictM2());
    }

    @Test
    void getById_returnNull_whenDistrictNotExist() {
        // Arrange
        District district = TestUtilsGenerator.getNewDistrictWithId();

        // Act
        District actual = districtRepository.getById(district.getDistrictId());

        // Assert
        assertThat(actual).isNull();
    }

    @Test
    void save_returnDistrict_whenNewDistrict() {
        // Arrange
        District district = TestUtilsGenerator.getNewDistrict();

        // Act
        District savedDistrict = districtRepository.save(district);

        // Assert
        assertThat(savedDistrict).isNotNull();
        assertThat(savedDistrict.getDistrictId()).isPositive();
        assertEquals(savedDistrict.getDistrictId(), district.getDistrictId());
        assertEquals(savedDistrict.getDistrictName(), district.getDistrictName());
        assertEquals(savedDistrict.getValueDistrictM2(), district.getValueDistrictM2());
    }

    @Test
    void save_updatedDistrict_whenDistrictWithId() {
        // Arrange
        District savedDistrict = districtRepository.save(TestUtilsGenerator.getNewDistrict());

        // Act
        savedDistrict.setDistrictName("Novo nome");
        savedDistrict.setValueDistrictM2(new BigDecimal("233.0"));
        District updatedDistrict = districtRepository.save(savedDistrict);

        // Assert
        assertThat(updatedDistrict).isNotNull();
        assertEquals(updatedDistrict.getDistrictId(), savedDistrict.getDistrictId());
        assertEquals(updatedDistrict.getDistrictName(), savedDistrict.getDistrictName());
        assertEquals(updatedDistrict.getValueDistrictM2(), savedDistrict.getValueDistrictM2());
    }

    @Test
    void save_generateNextId_whenDistrictIdExistsAndDistrictNotExist() {
        // Arrange
        District district = new District(15L, "Bairro", new BigDecimal(10));

        // Act
        districtRepository.save(district);
        District saved = districtRepository.getById(1L);

        // Assert
        assertThat(saved.getDistrictId()).isEqualTo(1L);
        assertThat(districtRepository.getById(15L)).isNull();
    }

    @Test
    void constructor_throwsRuntimeException_whenItCannotOpenPropertiesFile() {
        assertThrows(RuntimeException.class, () -> new DistrictRepository("naoexiste.properties"));
    }
}
