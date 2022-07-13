package com.betacampers.desafio_quality.controller;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
public class PropertyController {
    @Autowired
    private IPropertyService propertyService;

    @GetMapping("/area/{id}")
    public ResponseEntity<Double> getPropertyArea(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyArea(id));
    }

    @GetMapping("/value/{id}")
    public ResponseEntity<BigDecimal> getPropertyValue(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getPropertyValue(id));
    }

    @GetMapping("/largeRoom/{id}")
    public ResponseEntity<RoomResponseDto> getLargestRoom(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getLargestRoom(id));
    }

    @GetMapping("/roomsArea/{id}")
    public ResponseEntity<List<RoomResponseDto>> getRoomsArea(@PathVariable UUID id) {
        return ResponseEntity.ok(propertyService.getRoomsArea(id));
    }
}
