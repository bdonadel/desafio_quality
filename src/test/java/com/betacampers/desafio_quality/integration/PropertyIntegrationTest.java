package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
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

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IPropertyRepository repository;

    @BeforeEach
    public void clearData() {
        repository.clear();
    }

    @Test
    public void getPropertyArea_returnsPropertyArea_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/area"))
                .andExpect(status().isNotFound());

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/area"))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"));
    }

    @Test
    public void getPropertyValue_returnsPropertyValue_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        District district = new District(1, "Centro", new BigDecimal("10"));
        Property property = TestUtilsGenerator.getNewProperty(district, room1, room2);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/value"))
                .andExpect(status().isNotFound());

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/value"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf("5000.00")));
    }

    @Test
    public void getLargestRoom_returnsLargestRoom_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/largest-room"))
                .andExpect(status().isNotFound());

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/largest-room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$.roomArea").value(room2.getRoomLength() * room2.getRoomWidth()));
    }

    @Test
    public void getRoomsArea_returnRoomAreas_whenPropertyExists() throws Exception {
        Property property = TestUtilsGenerator.getNewProperty();
        Room room1 = property.getPropRooms().get(0);
        Room room2 = property.getPropRooms().get(1);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/roomsArea"))
                .andExpect(status().isNotFound());

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/roomsArea"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(room1.getRoomName()))
                .andExpect(jsonPath("$[0].roomArea").value(room1.getRoomLength() * room1.getRoomWidth()))
                .andExpect(jsonPath("$[1].roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$[1].roomArea").value(room2.getRoomLength() * room2.getRoomWidth()));
    }
}
