package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DistrictServiceTest {

    @InjectMocks
    DistrictService districtService;

    @Mock
    IDistrictRepository districtRepository;

    @BeforeEach
    public void setup() {
        when(districtRepository.save(ArgumentMatchers.any(District.class)))
                .thenReturn(TestUtilsGenerator.getNewDistrictWithId());

        when(districtRepository.getById(ArgumentMatchers.anyLong())).
                thenReturn(TestUtilsGenerator.getNewDistrictWithId());
    }

    @Test
    void save_returnDistrict_whenNewDistrict() {
        District district = TestUtilsGenerator.getNewDistrict();

        District savedDistrict = districtService.save(district);

        assertThat(savedDistrict.getDistrictId()).isPositive();
        assertThat(savedDistrict.getDistrictName()).isEqualTo(district.getDistrictName());
        assertThat(savedDistrict.getValueDistrictM2()).isNotNull();
        assertThat(savedDistrict.getValueDistrictM2()).isEqualByComparingTo(district.getValueDistrictM2());
        verify(districtRepository, atLeastOnce()).save(district);
    }

    @Test
    void getById_returnDistrict_whenDistrictExists() {
        District district = TestUtilsGenerator.getNewDistrictWithId();

        District districtFound = districtService.getById(district.getDistrictId());

        assertThat(districtFound.getDistrictId()).isEqualTo(district.getDistrictId());
        assertThat(districtFound.getDistrictId()).isNotNull();
        assertThat(districtFound.getDistrictName()).isEqualTo(district.getDistrictName());
        assertThat(districtFound.getDistrictName()).isNotBlank();
        assertThat(districtFound.getValueDistrictM2()).isEqualByComparingTo(district.getValueDistrictM2());
        assertThat(districtFound.getValueDistrictM2()).isNotNull();
        verify(districtRepository, atLeastOnce()).getById(district.getDistrictId());
    }

}
