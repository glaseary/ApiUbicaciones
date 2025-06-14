package com.Perfulandia.ApiUbicaciones.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiUbicaciones.dto.ComunaDTO;
import com.Perfulandia.ApiUbicaciones.dto.ProvinciaDTO;
import com.Perfulandia.ApiUbicaciones.models.Comuna;
import com.Perfulandia.ApiUbicaciones.models.Provincia;
import com.Perfulandia.ApiUbicaciones.repository.ComunaRepository;
import com.Perfulandia.ApiUbicaciones.repository.ProvinciaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComunaService {

    @Autowired
    private ComunaRepository comunaRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository; // Necesario para validar la provincia

    // Devuelve una lista de todas las comunas
    public List<ComunaDTO> listarComunas() {
        return comunaRepository.findAll().stream()
                .map(this::toComunaDTO)
                .collect(Collectors.toList());
    }
    
    // Busca una comuna por su ID
    public ComunaDTO obtenerComunaPorId(Integer id) {
        return comunaRepository.findById(id)
                .map(this::toComunaDTO)
                .orElseThrow(() -> new EntityNotFoundException("Comuna no encontrada con ID: " + id));
    }

    // Guarda una nueva comuna
    public ComunaDTO guardarComuna(ComunaDTO dto) {
        Comuna comuna = toComunaEntity(dto);
        Comuna saved = comunaRepository.save(comuna);
        return toComunaDTO(saved);
    }
    
    // Busca todas las comunas de una provincia específica
    public List<ComunaDTO> buscarComunasPorProvincia(Integer provinciaId) {
        if (!provinciaRepository.existsById(provinciaId)) {
            throw new EntityNotFoundException("No se pueden buscar comunas porque la Provincia con ID: " + provinciaId + " no existe.");
        }
        return comunaRepository.findByProvinciaIdProvincia(provinciaId).stream()
                .map(this::toComunaDTO)
                .collect(Collectors.toList());
    }

    //Métodos de Conversión

    private Comuna toComunaEntity(ComunaDTO dto) {
        Comuna comuna = new Comuna();
        comuna.setNombreComuna(dto.getNombreComuna());

        if (dto.getProvincia() != null && dto.getProvincia().getIdProvincia() != null) {
            Provincia provincia = provinciaRepository.findById(dto.getProvincia().getIdProvincia())
                    .orElseThrow(() -> new EntityNotFoundException("Provincia no encontrada con ID: " + dto.getProvincia().getIdProvincia()));
            comuna.setProvincia(provincia);
        } else {
             throw new IllegalArgumentException("El ID de la provincia es necesario para crear o actualizar una comuna.");
        }
        return comuna;
    }
    
    private ComunaDTO toComunaDTO(Comuna comuna) {
        ComunaDTO dto = new ComunaDTO();
        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombreComuna(comuna.getNombreComuna());
        if (comuna.getProvincia() != null) {
            dto.setProvincia(toProvinciaDTO(comuna.getProvincia()));
        }
        return dto;
    }

    private ProvinciaDTO toProvinciaDTO(Provincia provincia) {
        ProvinciaDTO dto = new ProvinciaDTO();
        dto.setIdProvincia(provincia.getIdProvincia());
        dto.setNombreProvincia(provincia.getNombreProvincia());
        return dto;
    }
}