package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    public void clearData() {
        TestUtilsGenerator.emptyUsersFile();
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);
        this.property = repository.save(TestUtilsGenerator.getNewProperty(district));
    }

    @Test
    public void getPropertyArea_returnsPropertyArea_whenPropertyExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/area"))
                .andExpect(status().isOk())
                .andExpect(content().string("22.75"));
    }

    @Test
    public void getPropertyValue_returnsPropertyValue_whenPropertyExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/value"))
                .andExpect(status().isOk())
                .andExpect(content().string("227.50"));
    }

    @Test
    public void getLargestRoom_returnsLargestRoom_whenPropertyExists() throws Exception {
        Room room1 = property.getPropRooms().get(0);

        mockMvc.perform(get("/api/v1/property/" + property.getPropId() + "/largest-room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(room1.getRoomName()))
                .andExpect(jsonPath("$.roomArea").value(room1.getRoomLength() * room1.getRoomWidth()));
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
    public void roomsArea_returnStatusNotFound_whenPropertyDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/property/123/roomsArea")).andExpect(status().isNotFound());
    }
}
