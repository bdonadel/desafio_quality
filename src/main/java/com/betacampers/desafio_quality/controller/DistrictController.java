package com.betacampers.desafio_quality.controller;

import com.betacampers.desafio_quality.model.District;
import com.betacampers.desafio_quality.service.IDistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller do District
 */
@RestController
@RequestMapping("/api/v1/district")
public class DistrictController {

    @Autowired
    private IDistrictService service;

    /**
     * Método do Controller que recebe um bairro e envia ao service para salvá-lo.
     *
     * @param district objeto District
     * @return O objeto District que foi salvo.
     */
    @PostMapping
    public ResponseEntity<District> createDistrict(@RequestBody @Valid District district) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(district));
    }

    /**
     * Método do Controller que retorna bairro pelo id enviado
     *
     * @param id valor Long representando o bairro
     * @return Objeto District.
     */
    @GetMapping("/{id}")
    public ResponseEntity<District> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

}
