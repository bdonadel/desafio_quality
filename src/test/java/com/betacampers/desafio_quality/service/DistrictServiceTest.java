package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DistrictServiceTest {

    @InjectMocks
    DistrictService districtService;

    @Mock
    IDistrictRepository districtRepository;

    @BeforeEach
    public void setup(){
        BDDMockito.when(districtRepository.save(ArgumentMatchers.any(District.class)))
                .thenReturn(TestUtilsGenerator.getNewDistrictWithId());

        BDDMockito.when(districtRepository.getById(ArgumentMatchers.anyLong())).
                thenReturn(TestUtilsGenerator.getNewDistrictWithId());
    }

    @Test
    void save_returnDistrict_whenNewDistrict() {

        District district = TestUtilsGenerator.getNewDistrict();

        District savedDistrict = districtService.save(district);

        assertThat(savedDistrict.getDistrictId()).isPositive();
        verify(districtRepository, atLeastOnce()).save(district);
    }

    @Test
    void getById_returnDistrict_whenDistrictExists() {
        District district = TestUtilsGenerator.getNewDistrictWithId();

        District districtFound = districtService.getById(district.getDistrictId());

        assertThat(districtFound.getDistrictId()).isEqualTo(district.getDistrictId());
        verify(districtRepository, atLeastOnce()).getById(district.getDistrictId());
    }
}
