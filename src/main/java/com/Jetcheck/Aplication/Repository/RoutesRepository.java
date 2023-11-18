package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoutesRepository extends JpaRepository<Rutas,String> {

}
