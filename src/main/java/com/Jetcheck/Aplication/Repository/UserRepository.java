package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuarios,String> {
    Optional<Usuarios> findByUsername(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}
