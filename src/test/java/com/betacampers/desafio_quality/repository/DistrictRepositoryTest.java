package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

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
    void getById_throwException_whenDistrictNotExist() {
        // Arrange
        District district = TestUtilsGenerator.getNewDistrictWithId();

        // Act
        DistrictNotFoundException exception = assertThrows(
                DistrictNotFoundException.class,
                () -> districtRepository.getById(district.getDistrictId()));

        // Assert
        assertThat(exception.getError().getDescription()).contains(district.getDistrictId().toString());
        assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
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
    void save_throwException_whenDistrictIdExistsAndDistrictNotExist() {
        // Arrange
        District district = TestUtilsGenerator.getNewDistrictWithId();

        // Act
        DistrictNotFoundException exception = assertThrows(
                DistrictNotFoundException.class,
                () -> districtRepository.save(district));

        // Assert
        assertThat(exception.getError().getDescription()).contains(district.getDistrictId().toString());
        assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void constructor_throwsRuntimeException_whenItCannotOpenPropertiesFile() {
        assertThrows(RuntimeException.class, () -> new DistrictRepository("naoexiste.properties"));
    }
}
