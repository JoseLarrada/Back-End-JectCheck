package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Calificaficiones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Calificaficiones,String> {

}
