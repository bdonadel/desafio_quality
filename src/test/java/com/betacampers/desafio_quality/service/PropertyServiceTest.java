package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyServiceTest {

    @InjectMocks
    PropertyService service;

    @Mock
    IPropertyRepository propertyRepository;

    @BeforeEach
    public void setup() {
        BDDMockito.when(propertyRepository.getById(ArgumentMatchers.anyLong()))
                .thenReturn(TestUtilsGenerator.getPropertyOk());
    }

    @Test
    void getPropertyValue_returnPropertyValue_whenPropertyIdExist() {
        Property property = TestUtilsGenerator.getPropertyOk();

        BigDecimal propertyValue = service.getPropertyValue(property.getPropId());
        double room1 = property.getPropRooms().get(0).getRoomWidth() * property.getPropRooms().get(0).getRoomLength();
        double room2 = property.getPropRooms().get(1).getRoomWidth() * property.getPropRooms().get(1).getRoomLength();
        double m2 = room1 + room2;
        BigDecimal resultValue = property.getPropDistrict().getValueDistrictM2().multiply(new BigDecimal(m2).setScale(2, RoundingMode.CEILING));

        Assertions.assertThat(resultValue).isEqualTo(propertyValue);
        Assertions.assertThat(propertyValue).isPositive();
        Assertions.assertThat(propertyValue).isNotNull();
        verify(propertyRepository, atLeastOnce()).getById(property.getPropId());
    }


}
