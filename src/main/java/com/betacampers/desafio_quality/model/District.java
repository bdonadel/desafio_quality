package com.betacampers.desafio_quality.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class District {
    private Long districtId;
    private String districtName;
    private BigDecimal valueDistrictM2;

    public District(String districtName, BigDecimal valueDistrictM2){
        this.districtName = districtName;
        this.valueDistrictM2 = valueDistrictM2;
    }
}
