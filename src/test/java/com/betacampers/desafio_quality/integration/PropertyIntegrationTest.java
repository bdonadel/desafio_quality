package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPropertyRepository repository;

    @Autowired
    private IDistrictRepository districtRepository;

    private Property property;

    @BeforeEach
    public void setup() {
        TestUtilsGenerator.emptyUsersFile();
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);
        this.property = repository.save(TestUtilsGenerator.getNewProperty(district));
    }

    @Test
    public void getPropertyArea_returnsPropertyArea_whenPropertyExists() throws Exception {
        // Arrange
        double correctValue = property.getPropRooms()
                .stream()
                .mapToDouble(r -> {
                    return r.getRoomLength() * r.getRoomWidth();
                })
                .sum();

        // Act
        MvcResult response = mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/area"))
                .andExpect(status().isOk())
                .andReturn();
        double propertyArea = Double.parseDouble(response.getResponse().getContentAsString());

        // Assert
        assertThat(propertyArea).isEqualTo(correctValue);
        assertThat(propertyArea).isNotNull();
        assertThat(propertyArea).isPositive();
    }

    @Test
    void getPropertyArea_returnException_whenPropertyWithoutRoom() throws Exception {
        // Arrange
        Property property = TestUtilsGenerator.getPropertyWithoutRoom();
        repository.save(property);

        // Act
        MvcResult response = mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/area"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        assertThat(response.getResponse().getContentAsString()).contains("PropertyWithoutRoomException");
        assertThat(response.getResponse().getContentAsString()).contains("" + property.getPropId());
    }

    @Test
    public void getPropertyArea_returnStatusNotFound_whenPropertyNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/50/area")).andExpect(status().isNotFound());
    }

    @Test
    public void getPropertyValue_returnsPropertyValue_whenPropertyExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/value"))
                .andExpect(status().isOk())
                .andExpect(content().string("227.50"));
    }

    @Test
    public void getPropertyValue_returnStatusNotFound_whenPropertyNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/50/value")).andExpect(status().isNotFound());
    }

    @Test
    void getLargestRoom_returnValue_whenPropertyExists() throws Exception {
        // Arrange
        double largestRoomArea = 0;
        for (Room room : property.getPropRooms()) {
            double area = room.getRoomLength() * room.getRoomWidth();
            if (area > largestRoomArea)
                largestRoomArea = area;
        }

        // Act
        MvcResult response = mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/largest-room"))
                .andExpect(status().isOk())
                .andReturn();
        String json = response.getResponse().getContentAsString();
        RoomResponseDto largestRoom = new ObjectMapper().readValue(json, RoomResponseDto.class);

        // Assert
        assertThat(largestRoom).isNotNull();
        assertThat(largestRoom.getRoomArea()).isPositive();
        assertThat(largestRoom.getRoomLength()).isPositive();
        assertThat(largestRoom.getRoomWidth()).isPositive();
        assertThat(largestRoom.getRoomName()).isNotBlank();
        assertThat(largestRoom.getRoomArea()).isEqualTo(largestRoomArea);
    }

    @Test
    void getLargestRoom_returnException_whenPropertyWithoutRoom() throws Exception {
        // Arrange
        Property property = TestUtilsGenerator.getPropertyWithoutRoom();
        repository.save(property);

        // Act
        MvcResult response = mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/largest-room"))
                .andExpect(status().isBadRequest())
                .andReturn();

        // Assert
        assertThat(response.getResponse().getContentAsString()).contains("PropertyWithoutRoomException");
        assertThat(response.getResponse().getContentAsString()).contains("" + property.getPropId());
    }

    @Test
    public void getLargestRoom_returnStatusNotFound_whenPropertyNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/50/largest-room")).andExpect(status().isNotFound());
    }

    @Test
    public void getRoomsArea_returnRoomAreas_whenPropertyExists() throws Exception {
        var room1 = property.getPropRooms().get(0);
        var room2 = property.getPropRooms().get(1);

        mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/roomsArea"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(room1.getRoomName()))
                .andExpect(jsonPath("$[0].roomArea").value(room1.getRoomLength() * room1.getRoomWidth()))
                .andExpect(jsonPath("$[1].roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$[1].roomArea").value(room2.getRoomLength() * room2.getRoomWidth()));
    }

    @Test
    public void getRoomsArea_returnStatusNotFound_whenPropertyDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/property/123/roomsArea")).andExpect(status().isNotFound());
    }
}
