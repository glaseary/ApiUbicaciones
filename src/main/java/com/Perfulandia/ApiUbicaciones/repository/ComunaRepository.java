package com.Perfulandia.ApiUbicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Perfulandia.ApiUbicaciones.models.Comuna;
import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {

    // Método para encontrar todas las comunas que pertenecen a una provincia específica.
    List<Comuna> findByProvinciaIdProvincia(Integer provinciaId);
}