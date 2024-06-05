package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Avances;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdvanceRepository extends JpaRepository<Avances,String> {
}
