package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyServiceTest {

    @InjectMocks
    PropertyService service;

    @Mock
    IPropertyRepository repository;

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

    @Test
    void getRoomsArea_returnListRooms_whenPropertyIdExist() {
        // Arrange
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);
        when(repository.getById(property.getPropId())).thenReturn(property);

        // Act
        List<RoomResponseDto> rooms = service.getRoomsArea(property.getPropId());
        RoomResponseDto roomDto1 = rooms.get(0);
        RoomResponseDto roomDto2 = rooms.get(1);

        // Assert
        assertThat(rooms.size()).isEqualTo(2);
        Assertions.assertEquals(roomDto1.getRoomArea(), room1.getRoomLength() * room1.getRoomWidth());
        Assertions.assertEquals(roomDto2.getRoomArea(), room2.getRoomLength() * room2.getRoomWidth());
    }
}
