package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class District {
    private Long districtId;
    private String districtName;
    private BigDecimal valueDistrictM2;
}
