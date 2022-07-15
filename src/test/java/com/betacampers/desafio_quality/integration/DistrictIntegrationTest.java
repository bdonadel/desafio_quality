package com.betacampers.desafio_quality.integration;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.repository.IDistrictRepository;
import com.betacampers.desafio_quality.util.TestUtilsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@Log4j2
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DistrictIntegrationTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private IDistrictRepository districtRepository;

    @BeforeEach
    public void setup() {
        TestUtilsGenerator.emptyUsersFile();
    }

    @Test
    public void postCreateDistrict_saveDistrict_whenNewDistrict() {
        District newDistrict = TestUtilsGenerator.getNewDistrict();
        String baseUrl = "http://localhost:" + port + "/api/v1/district";
        HttpEntity<District> httpEntity = new HttpEntity<>(newDistrict);

        ResponseEntity<District> response = testRestTemplate.exchange(baseUrl,
                HttpMethod.POST, httpEntity, District.class);

        District district = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(district).isNotNull();
        assertThat(district.getDistrictId()).isPositive();
        assertThat(district.getDistrictName()).isEqualTo(newDistrict.getDistrictName());
        assertThat(district.getValueDistrictM2()).isEqualTo(newDistrict.getValueDistrictM2());
    }

    @Test
    public void postCreateDistrict_returnStatusBadRequest_whenDistrictWithId() {
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);
        String baseUrl = "http://localhost:" + port + "/api/v1/district";
        HttpEntity<District> httpEntity = new HttpEntity<>(district);

        ResponseEntity<District> response = testRestTemplate.exchange(baseUrl,
                HttpMethod.POST, httpEntity, District.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    public void getFindById_returnDistrict_whenDistrictExist() {
        District district = TestUtilsGenerator.getNewDistrict();
        district = districtRepository.save(district);
        String baseUrl = "http://localhost:" + port + "/api/v1/district";

        ResponseEntity<District> response = testRestTemplate.exchange(baseUrl + "/" + district.getDistrictId(),
                HttpMethod.GET, null, District.class);

        District districtResponse = response.getBody();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(districtResponse).isNotNull();
        assertThat(districtResponse.getDistrictId()).isEqualTo(district.getDistrictId());
        assertThat(districtResponse.getDistrictName()).isEqualTo(district.getDistrictName());
        assertThat(districtResponse.getValueDistrictM2()).isEqualTo(district.getValueDistrictM2());
    }

    @Test
    public void getFindById_returnNotFound_whenDistrictNotExist() {
        String baseUrl = "http://localhost:" + port + "/api/v1/district";

        ResponseEntity<District> response = testRestTemplate.exchange(baseUrl + "/1",
                HttpMethod.GET, null, District.class);
        log.info(response.getBody() + "body");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
