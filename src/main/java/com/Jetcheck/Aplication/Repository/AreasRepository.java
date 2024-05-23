package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Areas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AreasRepository extends JpaRepository<Areas,Integer> {
    List<Areas> findAllByIdFacultad(int id);
}
