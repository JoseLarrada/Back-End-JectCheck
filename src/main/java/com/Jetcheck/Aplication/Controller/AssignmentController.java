package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.DTo.InfoAssignmentResponse;
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
    @DeleteMapping("/DeleteAssignment/{idAssignment}")
    public ResponseEntity<String> deleteAssignment(@PathVariable String idAssignment){
        return assignmentServices.deleteAssignment(idAssignment);
    }
    @PutMapping("/RateAssignment/{rate}/{idAssignment}")
    public ResponseEntity<String> rate(@PathVariable double rate, @PathVariable String idAssignment){
        return assignmentServices.rateAssignment(rate,idAssignment);
    }
    @GetMapping("/LoadAssignment/{idAssignment}")
    public ResponseEntity<List<Entregas>> loadAssignment(@PathVariable String idAssignment){
        return assignmentServices.deployAssignment(idAssignment);
    }
    @GetMapping("/getInfo/{id}")
    public ResponseEntity<InfoAssignmentResponse> getInfo(@PathVariable String id){
        return assignmentServices.getInfo(id);
    }
    @GetMapping("/getDescription/{id}")
    public ResponseEntity<String> getDescription(@PathVariable String id){
        return assignmentServices.getDescripcionById(id);
    }
}
