package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
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

        assertThat(roomDto1.getRoomArea())
                .isEqualTo(room1.getRoomLength() * room1.getRoomWidth());

        assertThat(roomDto2.getRoomArea())
                .isEqualTo(room2.getRoomLength() * room2.getRoomWidth());
    }
}