package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.exception.DistrictNotFoundException;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DistrictServiceTest {

    @InjectMocks
    DistrictService districtService;

    @Mock
    IDistrictRepository districtRepository;

    @Test
    void save_throws_whenIsGivenDistrictWithIdThatDoesNotExist() {
        District district = TestUtilsGenerator.getNewDistrictWithId();
        when(districtRepository.exists(district)).thenReturn(false);

        assertThrows(DistrictNotFoundException.class, () -> districtService.save(district));
        verify(districtRepository, never()).save(district);
    }

    @Test
    void save_doesNotThrow_whenIsGivenDistrictWithIdThatExists() {
        District district = TestUtilsGenerator.getNewDistrictWithId();
        when(districtRepository.exists(district)).thenReturn(true);

        assertDoesNotThrow(() -> districtService.save(district));
        verify(districtRepository, times(1)).save(district);
    }

    @Test
    void save_doesNotThrow_whenIsGivenDistrictWithoutId() {
        District district = TestUtilsGenerator.getNewDistrict();
        when(districtRepository.exists(district)).thenReturn(false);

        assertDoesNotThrow(() -> districtService.save(district));
        verify(districtRepository, times(1)).save(district);
    }

    @Test
    void getById_throws_whenRepositoryDoesNotContainDistrict() {
        District district = TestUtilsGenerator.getNewDistrictWithId();
        when(districtRepository.getById(district.getDistrictId())).thenReturn(null);
        when(districtRepository.exists(district)).thenReturn(false);

        assertThrows(DistrictNotFoundException.class, () -> districtService.getById(district.getDistrictId()));
    }

    @Test
    void getById_returnDistrict_whenRepositoryContainsDistrict() {
        District district = TestUtilsGenerator.getNewDistrictWithId();
        when(districtRepository.getById(district.getDistrictId())).thenReturn(district);

        assertEquals(district, districtService.getById(district.getDistrictId()));
        verify(districtRepository, times(1)).getById(district.getDistrictId());
    }
}
