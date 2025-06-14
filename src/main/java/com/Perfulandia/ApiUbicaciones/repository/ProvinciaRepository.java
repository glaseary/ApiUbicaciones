package com.Perfulandia.ApiUbicaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Perfulandia.ApiUbicaciones.models.Provincia;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
}
