package com.Perfulandia.ApiUbicaciones.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProvinciaDTO {
    private Integer idProvincia;
    private String nombreProvincia;
    private List<ComunaInfoDTO> comunas;
}