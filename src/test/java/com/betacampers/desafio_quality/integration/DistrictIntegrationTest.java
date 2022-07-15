package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DistrictIntegrationTest {

    final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IDistrictRepository districtRepository;

    @BeforeEach
    public void setup() {
        TestUtilsGenerator.emptyUsersFile();
    }

    @Test
    public void getFindById_returnDistrict_whenDistrictExist() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);

        MvcResult response = mockMvc.perform(get("/api/v1/district/" + district.getDistrictId()))
                .andExpect(status().isOk())
                .andReturn();

        String json = response.getResponse().getContentAsString();
        District districtResponse = new ObjectMapper().readValue(json, District.class);

        assertThat(districtResponse).isNotNull();
        assertThat(districtResponse.getDistrictId()).isEqualTo(district.getDistrictId());
        assertThat(districtResponse.getDistrictName()).isEqualTo(district.getDistrictName());
        assertThat(districtResponse.getValueDistrictM2()).isEqualTo(district.getValueDistrictM2());
    }

    @Test
    public void getFindById_returnNotFound_whenDistrictNotExist() throws Exception {

        mockMvc.perform(get("/api/v1/district/1"))
                .andExpect(status().isNotFound());
    }


    @Test
    public void postCreateDistrict_saveDistrict_whenNewDistrict() throws Exception {
        District newDistrict = TestUtilsGenerator.getNewDistrict();

        MvcResult response = mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(newDistrict))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        String json = response.getResponse().getContentAsString();
        District district = new ObjectMapper().readValue(json, District.class);

        assertThat(district).isNotNull();
        assertThat(district.getDistrictId()).isPositive();
        assertThat(district.getDistrictName()).isEqualTo(newDistrict.getDistrictName());
        assertThat(district.getValueDistrictM2()).isEqualTo(newDistrict.getValueDistrictM2());
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictWithId() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictNameIsBlank() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district.setDistrictName(null);

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.description", containsString("nome")));
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictNameFirstLetterNotCapital() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district.setDistrictName("bairro");

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.description", containsString("letra maiúscula")));
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictNameLengthGreaterThan45() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district.setDistrictName("bairrobairrobairrobairrobairrobairrobairrobairrobairrobairro");

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.description", containsString("45")));
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictValueIsEmpty() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district.setValueDistrictM2(null);

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.description", containsString("metro quadrado")));
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictValueHasMoreThan2DecimalPlaces() throws Exception {
        District district = TestUtilsGenerator.getNewDistrict();
        district.setValueDistrictM2(new BigDecimal("13.768"));

        mockMvc.perform(post("/api/v1/district")
                        .content(mapper.writeValueAsString(district))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name").value("MethodArgumentNotValidException"))
                .andExpect(jsonPath("$.description", containsString("2 casas decimais")));
    }
}
