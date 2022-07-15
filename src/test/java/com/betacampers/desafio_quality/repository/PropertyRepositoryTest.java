package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.exception.PropertyNotFoundException;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PropertyRepositoryTest {

    private IPropertyRepository propertyRepository;

    @BeforeEach
    void setup(){
        propertyRepository = new PropertyRepository();
    }

    @Test
    @DisplayName("GetById retorna a propriedade")
    void getById_returnProperty_whenPropertyExist() {
        Property p = TestUtilsGenerator.getNewProperty();
        propertyRepository.save(p);

        Property property = propertyRepository.getById(p.getPropId());

        assertThat(property).isNotNull();
        assertThat(property.getPropId()).isEqualTo(p.getPropId());
    }

    @Test
    void getById_throwException_whenPropertyNotExist() {
        Property p = TestUtilsGenerator.getNewProperty();
        propertyRepository.save(p);

        PropertyNotFoundException exception = Assertions.assertThrows(PropertyNotFoundException.class, () -> {
            Property property = propertyRepository.getById(1001L);
        });

        assertThat(exception.getError().getDescription()).contains("1001");
        assertThat(exception.getStatus()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    @Test
    void save_returnProperty_whenNewProperty() {
        Property p = TestUtilsGenerator.getNewProperty();

        Property savedProperty = propertyRepository.save(p);

        assertThat(savedProperty).isNotNull();

    }

    @Test
    void save_throwException_whenNull() {

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> {
            Property property = propertyRepository.save(null);
        });

    }

}
