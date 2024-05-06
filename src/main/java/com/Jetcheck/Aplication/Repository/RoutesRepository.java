package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Rutas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutesRepository extends JpaRepository<Rutas,String> {
    void deleteByNombre(String nombre);
    Optional<Rutas> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
