package com.betacampers.desafio_quality.integration;

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

    @BeforeEach
    public void clearData() {
        repository.clear();
    }

    @Test
    public void roomsArea_getRooms_whenPropertyExists() throws Exception {
        var property = TestUtilsGenerator.getNewProperty();
        var room1 = property.getPropRooms().get(0);
        var room2 = property.getPropRooms().get(1);

        //repository.save(property); TODO save property

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/roomsArea"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api/v1/" + property.getPropId() + "/roomsArea"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(room1.getRoomName()))
                .andExpect(jsonPath("$[0].roomArea").value(room1.getRoomLength() * room1.getRoomWidth()))
                .andExpect(jsonPath("$[1].roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$[1].roomArea").value(room2.getRoomLength() * room2.getRoomWidth()));
    }
    
    @Test
    public void roomsArea_returnStatusNotFound_whenPropertyDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/123/roomsArea")).andExpect(status().isNotFound());
    }
}
