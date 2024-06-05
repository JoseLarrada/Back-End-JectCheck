package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.DatosArchivos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileAssingmentRepository extends JpaRepository<DatosArchivos,String> {
    List<DatosArchivos> findAllByIdEntrega(String idAssingment);
}
