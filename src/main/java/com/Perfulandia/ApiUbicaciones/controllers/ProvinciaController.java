package com.Perfulandia.ApiUbicaciones.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Perfulandia.ApiUbicaciones.dto.ComunaDTO;
import com.Perfulandia.ApiUbicaciones.dto.ProvinciaDTO;
import com.Perfulandia.ApiUbicaciones.services.ComunaService;
import com.Perfulandia.ApiUbicaciones.services.ProvinciaService;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones/provincias")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;
    
    // Se inyecta ComunaService para manejar el sub-recurso /provincias/{id}/comunas
    @Autowired
    private ComunaService comunaService;

    @GetMapping
    public ResponseEntity<List<ProvinciaDTO>> listarProvincias() {
        return ResponseEntity.ok(provinciaService.listarProvincias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProvinciaDTO> obtenerProvinciaPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(provinciaService.obtenerProvinciaPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProvinciaDTO> crearProvincia(@RequestBody ProvinciaDTO dto) {
        return new ResponseEntity<>(provinciaService.guardarProvincia(dto), HttpStatus.CREATED);
    }
    
    // Endpoint para obtener todas las comunas de una provincia específica
    @GetMapping("/{provinciaId}/comunas")
    public ResponseEntity<List<ComunaDTO>> obtenerComunasPorProvincia(@PathVariable Integer provinciaId) {
        List<ComunaDTO> comunas = comunaService.buscarComunasPorProvincia(provinciaId);
        return ResponseEntity.ok(comunas);
    }
}