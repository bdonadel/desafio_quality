package com.betacampers.desafio_quality.controller;

import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {
    @Autowired
    private IPropertyService propertyService;

    @GetMapping("/{id}/area")
    public ResponseEntity<Double> getPropertyArea(@PathVariable long id) {
        return ResponseEntity.ok(propertyService.getPropertyArea(id));
    }

    @GetMapping("/{id}/value")
    public ResponseEntity<BigDecimal> getPropertyValue(@PathVariable long id) {
        return ResponseEntity.ok(propertyService.getPropertyValue(id));
    }

    @GetMapping("/{id}/largest-room")
    public ResponseEntity<RoomResponseDto> getLargestRoom(@PathVariable long id) {
        return ResponseEntity.ok(propertyService.getLargestRoom(id));
    }

    @GetMapping("/{id}/roomsArea")
    public ResponseEntity<List<RoomResponseDto>> getRoomsArea(@PathVariable long id) {
        return ResponseEntity.ok(propertyService.getRoomsArea(id));
    }

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }
}
