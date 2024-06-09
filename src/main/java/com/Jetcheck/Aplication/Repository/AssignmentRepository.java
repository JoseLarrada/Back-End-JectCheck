package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Entregas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Entregas,String> {
    List<Entregas> findAllByIdAvance(String id);
}
