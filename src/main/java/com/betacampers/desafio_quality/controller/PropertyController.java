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

/**
 * Controller do Property
 */
@RestController
@RequestMapping("/api/v1")
public class PropertyController {
    @Autowired
    private IPropertyService propertyService;

    /**
     * Método do Controller que retorna o valor da área total do imóvel
     * @param id valor Long representando o imóvel
     * @return Valor Double.
     */
    @GetMapping("/property/{id}/area")
    public ResponseEntity<Double> getPropertyArea(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyArea(id));
    }

    /**
     * Método do Controller que retorna o valor do imóvel
     * @param id valor Long representando o imóvel
     * @return Valor BigDecimal.
     */
    @GetMapping("/property/{id}/value")
    public ResponseEntity<BigDecimal> getPropertyValue(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getPropertyValue(id));
    }

    /**
     * Método do Controller que retorna o maior cômodo do imóvel
     * @param id valor Long representando o imóvel
     * @return Objeto RoomResponseDto contendo informações do cômodo.
     */
    @GetMapping("/property/{id}/largest-room")
    public ResponseEntity<RoomResponseDto> getLargestRoom(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getLargestRoom(id));
    }

    /**
     * Método do Controller que retorna lista de cômodos do imóvel e suas áreas
     * @param id valor Long representando o imóvel
     * @return Lista de RoomResponseDto contendo informações dos cômodos.
     */
    @GetMapping("/property/{id}/roomsArea")
    public ResponseEntity<List<RoomResponseDto>> getRoomsArea(@PathVariable Long id) {
        return ResponseEntity.ok(propertyService.getRoomsArea(id));
    }


    /**
     * Método do Controller que recebe um imóvel e envia ao service para salvá-lo.
     * @param property objeto PropertyRequestDto
     * @return O objeto Property que foi salvo.
     */
    @PostMapping("/property")
    public ResponseEntity<Property> saveProperty(@RequestBody @Valid PropertyRequestDto property) {
        return ResponseEntity.status(HttpStatus.CREATED).body(propertyService.saveProperty(property));
    }
}
