package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Facultades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Facultades,Integer> {
    Optional<Facultades> findByNombre(String name);
}
