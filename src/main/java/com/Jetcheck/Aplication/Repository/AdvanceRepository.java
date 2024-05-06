package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AdvanceRepository extends JpaRepository<Avances,String> {
    boolean existsByTitulo(String titulo);
    void deleteByTitulo(String nombre);
}
