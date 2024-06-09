package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Avances;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AdvanceRepository extends JpaRepository<Avances,String> {
    List<Avances> findAllByIdRuta(String string);
}
