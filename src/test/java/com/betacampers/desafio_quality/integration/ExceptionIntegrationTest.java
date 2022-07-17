package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.service.IDistrictService;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ExceptionIntegrationTest {
    final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDistrictService districtService;

    @Test
    void customExceptionHandler_handlesUntreatedExceptionAsInternalError() throws Exception {
        // Arrange
        when(districtService.save(any(District.class))).thenThrow(new RuntimeException());
        District district = TestUtilsGenerator.getNewDistrict();

        // Act & Assert
        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.name").value("Erro interno"))
                .andExpect(jsonPath("$.description", containsString("Ocorreu um erro interno no servidor.")));
    }

    @Test
    void customExceptionHandler_handlesEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/api/v1/district")
                        .content("")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("Requisição inválida"))
                .andExpect(jsonPath("$.description", containsString("A requisição está malformada.")));
    }
}
