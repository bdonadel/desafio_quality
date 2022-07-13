package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
public class District {
    private long districtId;
    private String districtName;
    private BigDecimal valueDistrictM2;
}
