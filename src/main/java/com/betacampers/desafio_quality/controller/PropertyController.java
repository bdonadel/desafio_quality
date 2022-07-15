package com.betacampers.desafio_quality.controller;

import com.betacampers.desafio_quality.dto.PropertyRequestDto;
import com.betacampers.desafio_quality.dto.RoomResponseDto;
import com.betacampers.desafio_quality.model.Property;
import com.betacampers.desafio_quality.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PropertyController {
    @Autowired
    private IPropertyService propertyService;

    @GetMapping("/property/{id}/area")
    public ResponseEntity<Double> getPropertyArea(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyArea(id));
    }

    @GetMapping("/property/{id}/value")
    public ResponseEntity<BigDecimal> getPropertyValue(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyValue(id));
    }

    @GetMapping("/property/{id}/largest-room")
    public ResponseEntity<RoomResponseDto> getLargestRoom(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getLargestRoom(id));
    }

    @GetMapping("/property/{id}/roomsArea")
    public ResponseEntity<List<RoomResponseDto>> getRoomsArea(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getRoomsArea(id));
    }

    @GetMapping("/properties")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    @PostMapping("/property")
    public ResponseEntity<Property> saveProperty(@RequestBody @Valid PropertyRequestDto property) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveProperty(property));
    }
}
