package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Services.AdvanceService;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/PrincipalContent")
@RequiredArgsConstructor
public class AdvanceController {
    private final AdvanceService advanceService;
    private final OtherRepository otherRepository;
    @PostMapping(value = "/createadvance")
    public ResponseEntity<String> CreateAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.CreateAdvance(advanceRequest);
    }

    @PutMapping(value = "/updateadvance")
    public ResponseEntity<String> UpdateAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.UpdateAdvance(advanceRequest);
    }
    @DeleteMapping(value = "/deleteadvance/{id}")
    public ResponseEntity<String> DeleteAdvance(@PathVariable String id){
        try{
            otherRepository.DeleteAdvanceFromName(id);
            return ResponseEntity.ok("Eliminado Correctamente");
        }catch (PersistenceException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping(value = "/chargueadvances/{id_route}")
    public ResponseEntity<List<Avances>> chargueadvances(@PathVariable String id_route){
        return  advanceService.DeployAdvances(id_route);
    }
}
