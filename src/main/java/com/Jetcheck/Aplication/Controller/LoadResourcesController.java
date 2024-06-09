package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Response.InteractiveSearch;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RepositoryJDBC;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LoadResourcesController {
    private final OtherRepository otherRepository;
    private final RepositoryJDBC repositoryJDBC;
    @GetMapping(value = "/getdepartmen")
    public ResponseEntity<List<String>> getDepartment(){
        return ResponseEntity.ok(otherRepository.getDepartments());
    }
    @GetMapping(value = "/getCity/{namedepartment}")
    public ResponseEntity<List<String>> getCity(@PathVariable String namedepartment){
        return ResponseEntity.ok(otherRepository.getCity(namedepartment));
    }
    @GetMapping(value = "/GetTeacher/{nameTeacher}")
    public ResponseEntity<List<InteractiveSearch>> existTeacher(@PathVariable String nameTeacher){
        return ResponseEntity.ok(repositoryJDBC.findUserInteractive(nameTeacher,"profesores"));
    }

    @GetMapping(value = "/GetStudent/{nameStudent}")
    public ResponseEntity<List<InteractiveSearch>> existStudent(@PathVariable String nameStudent){
        return ResponseEntity.ok(repositoryJDBC.findUserInteractive(nameStudent,"estudiantes"));
    }
}
