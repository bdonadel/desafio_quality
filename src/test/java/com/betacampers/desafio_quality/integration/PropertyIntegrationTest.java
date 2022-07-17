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
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
public class PropertyIntegrationTest {
    private final ObjectMapper mapper = new ObjectMapper();
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
        property = repository.save(TestUtilsGenerator.getNewProperty(district));
    }

    @Test
    public void getPropertyArea_returnsPropertyArea_whenPropertyExists() throws Exception {
        // Arrange
        double correctValue = property.getPropRooms()
                .stream()
                .mapToDouble(r -> r.getRoomLength() * r.getRoomWidth())
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
        assertThat(response.getResponse().getContentAsString(Charset.defaultCharset())).contains("Imóvel sem cômodos");
        assertThat(response.getResponse().getContentAsString(Charset.defaultCharset())).contains("" + property.getPropId());
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
        assertThat(response.getResponse().getContentAsString(Charset.defaultCharset())).contains("Imóvel sem cômodos");
        assertThat(response.getResponse().getContentAsString(Charset.defaultCharset())).contains("" + property.getPropId());
    }

    @Test
    public void getLargestRoom_returnStatusNotFound_whenPropertyNotExists() throws Exception {
        mockMvc.perform(get("/api/v1/property/50/largest-room")).andExpect(status().isNotFound());
    }

    @Test
    public void getRoomsArea_returnRoomAreas_whenPropertyExists() throws Exception {
        Room room1 = property.getPropRooms().get(0);
        Room room2 = property.getPropRooms().get(1);

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

    @Test
    public void postSaveProperty_returnProperty_whenNewProperty() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();

        MvcResult response = mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.propName").value(newProperty.getPropName()))
                .andExpect(jsonPath("$.propDistrict.districtId").value(newProperty.getDistrictId()))
                .andExpect(jsonPath("$.propRooms[0].roomName").value(newProperty.getPropRooms().get(0).getRoomName()))
                .andExpect(jsonPath("$.propRooms[1].roomName").value(newProperty.getPropRooms().get(1).getRoomName()))
                .andReturn();

        assertThat(response.getResponse().getErrorMessage()).isNull();
    }

    @Test
    public void postSaveProperty_returnProperty_whenDistrictIdNoExist() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setDistrictId(250L);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.name").value("Bairro não encontrado"))
                .andExpect(jsonPath("$.description", containsString("bairro")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenPropNameNull() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setPropName(null);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("estar vazio")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenPropNameLowerCase() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setPropName("casa");

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("letra maiúscula")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenPropNameLarge() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setPropName("Lorem ipsum dolor sit amet cons");

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("exceder 30 caracteres")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenDistrictIdIsNull() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setDistrictId(null);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("bairro")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenPropertyHasNoRooms() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.setPropRooms(null);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("cômodo")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomNameIsNull() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomName(null);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("estar vazio")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomNameLowerCase() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomName("quarto");

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("letra maiúscula")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomNameLarge() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomName("Lorem ipsum dolor sit amet cons");

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("exceder 30 caracteres")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomWidthZero() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomWidth(0);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("largura do cômodo")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomWidthNegative() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomWidth(-5);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("largura do cômodo")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomWidthLarge() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomWidth(25.1);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("largura máxima")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomLengthZero() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomLength(0);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("comprimento do cômodo")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomLengthNegative() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomLength(-5);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("comprimento do cômodo")));
    }

    @Test
    public void postSaveProperty_returnBadRequest_whenRoomLengthLarge() throws Exception {
        PropertyRequestDto newProperty = TestUtilsGenerator.getNewPropertyRequest();
        newProperty.getPropRooms().get(0).setRoomLength(33.1);

        mockMvc.perform(post("/api/v1/property/")
                        .content(mapper.writeValueAsString(newProperty))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("comprimento máximo")));
    }

    @Test
    public void postSaveProperty_returnStatusBadRequest_whenSendingNonArrayInPlaceOfArray() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("propName", "teste");
        body.put("districtId", 1);
        body.put("propRooms", "a"); // invalid

        mockMvc.perform(post("/api/v1/property")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("esperado array")));
    }

    @Test
    public void postSaveProperty_returnStatusBadRequest_whenSendingNonStringInPlaceOfString() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("propName", List.of());
        body.put("districtId", 1);
        body.put("propRooms", List.of()); // invalid

        mockMvc.perform(post("/api/v1/property")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("esperado string")));
    }

    @Test
    public void postSaveProperty_returnStatusBadRequest_whenSendingNonObjectInPlaceOfObject() throws Exception {
        Map<String, Object> body = new HashMap<>();
        body.put("propName", "Teste");
        body.put("districtId", 1);
        body.put("propRooms", List.of(1)); // invalid

        mockMvc.perform(post("/api/v1/property")
                        .content(mapper.writeValueAsString(body))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Campo(s) inválido(s)"))
                .andExpect(jsonPath("$.description", containsString("esperado object")));
    }
}
