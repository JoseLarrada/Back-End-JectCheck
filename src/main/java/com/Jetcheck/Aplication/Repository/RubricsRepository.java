package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Rubricas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RubricsRepository extends JpaRepository<Rubricas,String> {
}
