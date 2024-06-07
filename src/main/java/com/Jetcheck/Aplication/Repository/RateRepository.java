package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Calificaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RateRepository extends JpaRepository<Calificaciones,String> {
    Optional<Calificaciones> findByIdEntrega(String id);
}
