package com.betacampers.desafio_quality.service;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PropertyServiceTest {

    @InjectMocks
    PropertyService service;

    @Mock
    IPropertyRepository repository;

    @Test
    void getRoomsArea_returnListRooms_whenPropertyIdExist() {
        Property property = TestUtilsGenerator.getPropertyWithId();
        List<Room> originalRooms = property.getPropRooms();
        BDDMockito.when(repository.getById(5)).thenReturn(property);

        List<RoomResponseDto> rooms = service.getRoomsArea(5);

        Assertions.assertThat(rooms.size()).isEqualTo(originalRooms.size());
        for (int i = 0; i < property.getPropRooms().size(); i++) {
            Assertions.assertThat(rooms.get(i).getRoomArea())
                    .isEqualTo(originalRooms.get(i).getRoomLength() * originalRooms.get(i).getRoomWidth());
        }
    }
}