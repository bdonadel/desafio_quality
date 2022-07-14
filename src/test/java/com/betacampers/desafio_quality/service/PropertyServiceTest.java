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
    void getPropertyArea_when() {
        Property property = TestUtilsGenerator.getPropertyOk();

        BigDecimal propertyValue = service.getPropertyValue(property.getPropId());

        Assertions.assertThat(propertyValue).isPositive();
        Assertions.assertThat(propertyValue).isNotNull();
    }


}
