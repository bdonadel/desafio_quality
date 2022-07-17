package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.exception.PropertyWithoutRoomException;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyServiceTest {

    @InjectMocks
    PropertyService service;

    @Mock
    IPropertyRepository propertyRepository;

    @Mock
    IDistrictRepository districtRepository;

    @BeforeEach
    public void setup() {
        when(propertyRepository.getById(ArgumentMatchers.anyLong()))
                .thenReturn(TestUtilsGenerator.getNewProperty());
    }

    @Test
    void saveProperty_returnProperty_whenNewProperty() {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        when(districtRepository.getById(ArgumentMatchers.anyLong()))
                .thenReturn(TestUtilsGenerator.getNewDistrictWithId());

        when(propertyRepository.save(ArgumentMatchers.any(Property.class)))
                .thenReturn(TestUtilsGenerator.getNewProperty(TestUtilsGenerator.getNewDistrictWithId(),
                        newProperty.getPropRooms().get(0), newProperty.getPropRooms().get(1)));

        Property property = service.saveProperty(newProperty);

        assertThat(property.getPropId()).isPositive();
        assertEquals(property.getPropName(), newProperty.getPropName());
        assertEquals(property.getPropRooms().get(0), newProperty.getPropRooms().get(0));
        assertEquals(property.getPropRooms().get(1), newProperty.getPropRooms().get(1));
        assertEquals(property.getPropDistrict().getDistrictId(), newProperty.getDistrictId());
    }

    @Test
    void getPropertyValue_returnPropertyValue_whenPropertyIdExist() {
        Property property = TestUtilsGenerator.getNewProperty();

        BigDecimal propertyValue = service.getPropertyValue(property.getPropId());
        double room1 = property.getPropRooms().get(0).getRoomWidth() * property.getPropRooms().get(0).getRoomLength();
        double room2 = property.getPropRooms().get(1).getRoomWidth() * property.getPropRooms().get(1).getRoomLength();
        double m2 = room1 + room2;
        BigDecimal resultValue =
                property.getPropDistrict().getValueDistrictM2().multiply(new BigDecimal(m2).setScale(2,
                        RoundingMode.CEILING));

        assertThat(resultValue).isEqualTo(propertyValue);
        assertThat(propertyValue).isPositive();
        assertThat(propertyValue).isNotNull();
        verify(propertyRepository, atLeastOnce()).getById(property.getPropId());
    }

    @Test
    void getRoomsArea_returnListRooms_whenPropertyIdExist() {
        // Arrange
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);
        when(propertyRepository.getById(property.getPropId())).thenReturn(property);

        // Act
        List<RoomResponseDto> rooms = service.getRoomsArea(property.getPropId());
        RoomResponseDto roomDto1 = rooms.get(0);
        RoomResponseDto roomDto2 = rooms.get(1);

        // Assert
        assertThat(rooms.size()).isEqualTo(2);
        assertEquals(roomDto1.getRoomArea(), room1.getRoomLength() * room1.getRoomWidth());
        assertEquals(roomDto2.getRoomArea(), room2.getRoomLength() * room2.getRoomWidth());
    }


    @Test
    void getPropertyArea_returnValue_whenPropertyExists() {
        // Arrange
        Property property = TestUtilsGenerator.getNewProperty();
        when(propertyRepository.getById(property.getPropId())).thenReturn(property);
        double correctValue = property.getPropRooms()
                .stream()
                .mapToDouble(r -> r.getRoomLength() * r.getRoomWidth())
                .sum();

        // Act
        double propertyArea = service.getPropertyArea(property.getPropId());

        // Assert
        assertThat(propertyArea).isEqualTo(correctValue);
        assertThat(propertyArea).isPositive();
        assertThat(propertyArea).isNotNull();
        verify(propertyRepository, atLeastOnce()).getById(property.getPropId());
    }

    @Test
    void getPropertyArea_returnException_whenPropertyWithoutRoom() {
        // Arrange
        Property property = TestUtilsGenerator.getPropertyWithoutRoom();
        when(propertyRepository.getById(property.getPropId())).thenReturn(property);

        // Act
        PropertyWithoutRoomException exception = Assertions.assertThrows(
                PropertyWithoutRoomException.class,
                () -> service.getPropertyArea(property.getPropId()));

        //Assert
        assertThat(exception.getError().getDescription()).contains("" + property.getPropId());
        assertThat(exception.getError().getName()).isEqualTo("Imóvel sem cômodos");
    }

    @Test
    void getLargestRoom_returnValue_whenPropertyExists() {
        // Arrange
        Property property = TestUtilsGenerator.getNewProperty();
        when(propertyRepository.getById(property.getPropId())).thenReturn(property);
        double largestRoomArea = 0;
        for (Room room : property.getPropRooms()) {
            double area = room.getRoomLength() * room.getRoomWidth();
            if (area > largestRoomArea)
                largestRoomArea = area;
        }

        // Act
        RoomResponseDto largestRoom = service.getLargestRoom(property.getPropId());

        // Assert
        assertThat(largestRoom).isNotNull();
        assertThat(largestRoom.getRoomArea()).isPositive();
        assertThat(largestRoom.getRoomLength()).isPositive();
        assertThat(largestRoom.getRoomWidth()).isPositive();
        assertThat(largestRoom.getRoomName()).isNotBlank();
        assertThat(largestRoom.getRoomArea()).isEqualTo(largestRoomArea);
    }

    @Test
    void getLargestRoom_returnException_whenPropertyWithoutRoom() {
        // Arrange
        Property property = TestUtilsGenerator.getPropertyWithoutRoom();
        when(propertyRepository.getById(property.getPropId())).thenReturn(property);

        // Act
        PropertyWithoutRoomException exception = Assertions.assertThrows(
                PropertyWithoutRoomException.class,
                () -> service.getLargestRoom(property.getPropId()));

        // Assert
        assertThat(exception.getError().getDescription()).contains("" + property.getPropId());
        assertThat(exception.getError().getName()).isEqualTo("Imóvel sem cômodos");
    }

}
