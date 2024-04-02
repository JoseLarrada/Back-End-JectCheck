package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Services.AssignmentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/PrincipalContent")
public class AssignmentController {
    private final AssignmentServices assignmentServices;
    @PostMapping("/addAssignment")
    public ResponseEntity<String> createAssignment(@RequestBody AssignmentRequest request){
        return assignmentServices.addAssignment(request);
    }
    @PutMapping("/UpdateAssignment")
    public ResponseEntity<String> updateAssignment(@RequestBody AssignmentRequest request){
        return assignmentServices.modifyAssignment(request);
    }
    @DeleteMapping("/DeleteAssignment")
    public ResponseEntity<String> deleteAssignment(@RequestParam String Id_Assignment){
        return assignmentServices.deleteAssignment(Id_Assignment);
    }
    @PutMapping("/RateAssignment")
    public ResponseEntity<String> rate(@RequestParam double rate, @RequestParam String Id_Assignment){
        return assignmentServices.rateAssignment(rate,Id_Assignment);
    }
    @GetMapping("/LoadAssignment")
    public ResponseEntity<List<Entregas>> loadAssignment(@RequestParam String id_advance){
        return assignmentServices.deployAssignment(id_advance);
    }
}
