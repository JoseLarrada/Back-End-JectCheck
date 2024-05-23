package com.Jetcheck.Aplication.Repository;

import com.Jetcheck.Aplication.Entity.Facultades;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacultyRepository extends JpaRepository<Facultades,Integer> {

}
