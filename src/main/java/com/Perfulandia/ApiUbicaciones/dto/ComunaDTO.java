package com.Perfulandia.ApiUbicaciones.dto;

import lombok.Data;

@Data
public class ComunaDTO {
    private Integer idComuna;
    private String nombreComuna;
    private ProvinciaDTO provincia; // Para saber a qué provincia pertenece la comuna
}