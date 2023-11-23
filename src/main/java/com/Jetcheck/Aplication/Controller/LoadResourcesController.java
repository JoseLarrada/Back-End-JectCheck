package com.Jetcheck.Aplication.Controller;

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
    public ResponseEntity<String> existTeacher(@PathVariable String nameTeacher){
        String id=repositoryJDBC.GetIdTeacherByName(nameTeacher);
        if (repositoryJDBC.ExistTeacher(id)){
            return ResponseEntity.ok("Si existe el docente");
        }
        return ResponseEntity.badRequest().body("No existe el docente");
    }

    @GetMapping(value = "/GetStudent/{nameStudent}")
    public ResponseEntity<String> existStudent(@PathVariable String nameStudent){
        String id=repositoryJDBC.GetIdStudentByName(nameStudent);
        if (repositoryJDBC.ExistMember(id)){
            return ResponseEntity.ok("Si existe el estudiante");
        }
        return ResponseEntity.badRequest().body("No existe el estudiante");
    }
}
