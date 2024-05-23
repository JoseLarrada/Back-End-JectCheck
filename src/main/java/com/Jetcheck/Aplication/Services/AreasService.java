package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Entity.Areas;
import com.Jetcheck.Aplication.Entity.Facultades;
import com.Jetcheck.Aplication.Repository.AreasRepository;
import com.Jetcheck.Aplication.Repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.List;

@Service
@RequiredArgsConstructor
public class AreasService {
    private final FacultyRepository facultyRepository;
    private final AreasRepository areasRepository;

    public ResponseEntity<List<Areas>> getAreas(String nameFacultly){
        Facultades facultly= facultyRepository.findByNombre(nameFacultly).orElse(null);
        return ResponseEntity.ok(areasRepository.findAllByIdFacultad(facultly.getIdFacultad()));
    }
    public ResponseEntity<List<Facultades>> getFacultly(){
        return ResponseEntity.ok(facultyRepository.findAll());
    }
}
