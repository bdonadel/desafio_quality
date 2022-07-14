package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.model.Room;
import com.betacampers.desafio_quality.repository.IPropertyRepository;
import com.betacampers.desafio_quality.snippets.CustomHttpRequestSnippet;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class OldPropertyIntegrationTest {
    @Autowired
    private IPropertyRepository repository;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        var config = documentationConfiguration(restDocumentation);
        config.operationPreprocessors().withResponseDefaults(prettyPrint());
        config.snippets().withAdditionalDefaults(new CustomHttpRequestSnippet());

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(config).build();
    }

    @BeforeEach
    public void clearData() {
        repository.clear();
    }

    @Test
    public void getPropertyArea_returnsPropertyArea_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);

        mockMvc.perform(get("/api/v1/{propertyId}/area", property.getPropId()))
                .andExpect(status().isNotFound())
                .andDo(document("area-not-found"));

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/{propertyId}/area", property.getPropId()))
                .andExpect(status().isOk())
                .andExpect(content().string("500.0"))
                .andDo(document("area-ok", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));
    }

    @Test
    public void getPropertyValue_returnsPropertyValue_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        District district = new District(1, "Centro", new BigDecimal("10"));
        Property property = TestUtilsGenerator.getNewProperty(district, room1, room2);

        mockMvc.perform(get("/api/v1/{propertyId}/value", property.getPropId()))
                .andExpect(status().isNotFound())
                .andDo(document("value-not-found", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/{propertyId}/value", property.getPropId()))
                .andExpect(status().isOk())
                .andExpect(content().string("5000.00"))
                .andDo(document("value-ok", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));
    }

    @Test
    public void getLargestRoom_returnsLargestRoom_whenPropertyExists() throws Exception {
        Room room1 = new Room("Sala", 10, 10);
        Room room2 = new Room("Quarto", 20, 20);
        Property property = TestUtilsGenerator.getNewProperty(room1, room2);

        mockMvc.perform(get("/api/v1/{propertyId}/largest-room", property.getPropId()))
                .andExpect(status().isNotFound())
                .andDo(document("largest-room-not-found", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/{propertyId}/largest-room", property.getPropId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$.roomArea").value(room2.getRoomLength() * room2.getRoomWidth()))
                .andDo(document("largest-room-ok", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));
    }

    @Test
    public void getRoomsArea_returnRoomAreas_whenPropertyExists() throws Exception {
        Property property = TestUtilsGenerator.getNewProperty();
        Room room1 = property.getPropRooms().get(0);
        Room room2 = property.getPropRooms().get(1);

        mockMvc.perform(get("/api/v1/{propertyId}/roomsArea", property.getPropId()))
                .andExpect(status().isNotFound())
                .andDo(document("rooms-area-not-found", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));

        repository.addProperty(property);

        mockMvc.perform(get("/api/v1/{propertyId}/roomsArea", property.getPropId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomName").value(room1.getRoomName()))
                .andExpect(jsonPath("$[0].roomArea").value(room1.getRoomLength() * room1.getRoomWidth()))
                .andExpect(jsonPath("$[1].roomName").value(room2.getRoomName()))
                .andExpect(jsonPath("$[1].roomArea").value(room2.getRoomLength() * room2.getRoomWidth()))
                .andDo(document("rooms-area-ok", pathParameters(
                        parameterWithName("propertyId").description("O ID do imóvel")
                )));
    }
}
