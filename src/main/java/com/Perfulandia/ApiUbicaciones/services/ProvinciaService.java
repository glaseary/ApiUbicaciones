package com.Perfulandia.ApiUbicaciones.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Perfulandia.ApiUbicaciones.dto.ComunaInfoDTO;
import com.Perfulandia.ApiUbicaciones.dto.ProvinciaDTO;
import com.Perfulandia.ApiUbicaciones.models.Comuna;
import com.Perfulandia.ApiUbicaciones.models.Provincia;
import com.Perfulandia.ApiUbicaciones.repository.ComunaRepository;
import com.Perfulandia.ApiUbicaciones.repository.ProvinciaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ComunaRepository comunaRepository;

    // Devuelve una lista de todas las provincias
    public List<ProvinciaDTO> listarProvincias() {
        return provinciaRepository.findAll().stream()
                .map(this::toProvinciaDTO)
                .collect(Collectors.toList());
    }

    // Busca una provincia por su ID
    public ProvinciaDTO obtenerProvinciaPorId(Integer id) {
        return provinciaRepository.findById(id)
                .map(this::toProvinciaDTO)
                .orElseThrow(() -> new EntityNotFoundException("Provincia no encontrada con ID: " + id));
    }
    
   // Guarda una nueva provincia (no devuelve la lista de comunas al crear)
    public ProvinciaDTO guardarProvincia(ProvinciaDTO dto) {
        Provincia provincia = new Provincia();
        provincia.setNombreProvincia(dto.getNombreProvincia());
        Provincia saved = provinciaRepository.save(provincia);
        
        // Al crear, se devuelve sin la lista de comunas, ya que no tiene.
        ProvinciaDTO resultDto = new ProvinciaDTO();
        resultDto.setIdProvincia(saved.getIdProvincia());
        resultDto.setNombreProvincia(saved.getNombreProvincia());
        return resultDto;
    }

    // metodos de conversion

    // metodo que carga las comunas
    private ProvinciaDTO toProvinciaDTO(Provincia provincia) {
        ProvinciaDTO dto = new ProvinciaDTO();
        dto.setIdProvincia(provincia.getIdProvincia());
        dto.setNombreProvincia(provincia.getNombreProvincia());
        
        // 1. Buscar todas las comunas asociadas a esta provincia
        List<Comuna> comunasDeLaProvincia = comunaRepository.findByProvinciaIdProvincia(provincia.getIdProvincia());
        
        // 2. Convertir la lista de entidades Comuna a una lista de ComunaInfoDTO
        dto.setComunas(comunasDeLaProvincia.stream()
                .map(this::toComunaInfoDTO)
                .collect(Collectors.toList()));
        
        return dto;
    }
    
    // método de conversión para el DTO simplificado
    private ComunaInfoDTO toComunaInfoDTO(Comuna comuna) {
        ComunaInfoDTO dto = new ComunaInfoDTO();
        dto.setIdComuna(comuna.getIdComuna());
        dto.setNombreComuna(comuna.getNombreComuna());
        return dto;
    }
}