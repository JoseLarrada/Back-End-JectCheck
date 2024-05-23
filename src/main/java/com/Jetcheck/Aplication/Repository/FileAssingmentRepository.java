package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.DatosArchivos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileAssingmentRepository extends JpaRepository<DatosArchivos,String> {
}
