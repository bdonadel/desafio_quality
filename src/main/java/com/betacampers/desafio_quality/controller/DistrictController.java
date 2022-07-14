package com.betacampers.desafio_quality.controller;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.service.IDistrictService;
import com.betacampers.desafio_quality.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/district")
public class DistrictController {

    @Autowired
    private IDistrictService service;

    @PostMapping
    public ResponseEntity<District> createDistrict(@RequestBody District district) {
        if(district.getDistrictId() != null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(district));
    }

    @GetMapping("/{id}")
    public ResponseEntity<District> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

}
