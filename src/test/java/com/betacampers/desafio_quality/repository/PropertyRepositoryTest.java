package com.betacampers.desafio_quality.repository;

import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class PropertyRepositoryTest {

    private IPropertyRepository propertyRepository;

    @BeforeEach
    void setup() {
        propertyRepository = new PropertyRepository();
        TestUtilsGenerator.emptyUsersFile();
    }

    @Test
    void getById_returnProperty_whenPropertyExist() {
        // Arrange
        Property savedProperty = propertyRepository.save(TestUtilsGenerator.getNewProperty());

        // Act
        Property foundProperty = propertyRepository.getById(savedProperty.getPropId());

        // Assert
        assertThat(foundProperty).isNotNull();
        assertEquals(foundProperty.getPropId(), savedProperty.getPropId());
        assertEquals(foundProperty.getPropName(), savedProperty.getPropName());
        assertEquals(foundProperty.getPropDistrict(), savedProperty.getPropDistrict());
        assertEquals(foundProperty.getPropRooms(), savedProperty.getPropRooms());
    }

    @Test
    void getById_returnNull_whenPropertyDoesNotExist() {
        // Arrange
        Property savedProperty = TestUtilsGenerator.getNewProperty();
        propertyRepository.save(savedProperty);

        // Act
        Property property = propertyRepository.getById(1001L);

        // Assert
        assertThat(property).isNull();
    }

    @Test
    void save_returnProperty_whenNewProperty() {
        // Arrange
        Property newProperty = TestUtilsGenerator.getNewProperty();

        // Act
        Property savedProperty = propertyRepository.save(newProperty);

        // Assert
        assertThat(savedProperty).isNotNull();
        assertEquals(savedProperty.getPropId(), savedProperty.getPropId());
        assertEquals(savedProperty.getPropName(), savedProperty.getPropName());
        assertEquals(savedProperty.getPropDistrict(), savedProperty.getPropDistrict());
        assertEquals(savedProperty.getPropRooms(), savedProperty.getPropRooms());
    }

    @Test
    void save_throwException_whenNullParam() {
        // Arrange & Act
        assertThrows(RuntimeException.class, () -> {
            Property savedProperty = propertyRepository.save(null);
            assertThat(savedProperty).isNull();
        });
    }

    @Test
    void constructor_throwsRuntimeException_whenItCannotOpenPropertiesFile() {
        assertThrows(RuntimeException.class, () -> new PropertyRepository("naoexiste.properties"));
    }
}
