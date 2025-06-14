package com.Perfulandia.ApiUbicaciones.controllers;

import com.Perfulandia.ApiUbicaciones.dto.ComunaDTO;
import com.Perfulandia.ApiUbicaciones.services.ComunaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones/comunas")
public class ComunaController {

    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ComunaDTO>> listarComunas() {
        return ResponseEntity.ok(comunaService.listarComunas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComunaDTO> obtenerComunaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(comunaService.obtenerComunaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ComunaDTO> crearComuna(@RequestBody ComunaDTO dto) {
        return new ResponseEntity<>(comunaService.guardarComuna(dto), HttpStatus.CREATED);
    }
}