package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.Entity.Areas;
import com.Jetcheck.Aplication.Entity.Facultades;
import com.Jetcheck.Aplication.Repository.AreasRepository;
import com.Jetcheck.Aplication.Repository.FacultyRepository;
import com.Jetcheck.Aplication.Services.AreasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/investigationArea")
public class investigacionAreaController {
    private final AreasService areasService;
    @GetMapping("/facultly")
    public ResponseEntity<List<Facultades>> getFacultly(){
        return areasService.getFacultly();
    }
    @GetMapping("/areas/{name}")
    public ResponseEntity<List<Areas>> getAreas(@PathVariable String name){
        return areasService.getAreas(name);
    }
}
