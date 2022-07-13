package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.CustomError;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.repository.PropertyRepository;
import com.betacampers.desafio_quality.service.PropertyService;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropertyIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;

    //@Mock
    @Autowired
    IPropertyRepository repository;

    @Test
    public void roomsArea_getRooms_whenPropertyExist() {
        /*Property property = TestUtilsGenerator.getPropertyWithId();
        List<Room> originalRooms = property.getPropRooms();
        BDDMockito.when(repository.getById(5)).thenReturn(property); //TODO pode isso?
        */
        long propertyId = 1;
        Property property = repository.getById(propertyId);
        List<Room> originalRooms = property.getPropRooms();

        String url = "http://localhost:" + port + "/api/v1/" + propertyId + "/roomsArea";

        var retorno = testRestTemplate.exchange(url,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<RoomResponseDto>>(){});

        List<RoomResponseDto> rooms = retorno.getBody();

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(rooms).isNotNull();
        assertThat(rooms.get(0).getRoomArea()).isPositive();
        assertThat(rooms.get(0).getRoomArea())
                .isEqualTo(originalRooms.get(0).getRoomLength() * originalRooms.get(0).getRoomWidth());
    }

    @Test
    public void roomsArea_returnStatusNotFound_whenPropertyNoExist() {
        String url = "http://localhost:" + port + "/api/v1/555/roomsArea";

        var retorno = testRestTemplate.exchange(url,
                    HttpMethod.GET, null, CustomError.class);

        assertThat(retorno.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
