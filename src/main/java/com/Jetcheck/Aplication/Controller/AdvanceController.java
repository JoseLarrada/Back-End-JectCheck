package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Services.AdvanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/PrincipalContent")
@RequiredArgsConstructor
public class AdvanceController {
    private final AdvanceService advanceService;
    @PostMapping(value = "/createadvance")
    public ResponseEntity<String> CreateAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.CreateAdvance(advanceRequest);
    }

    @PutMapping(value = "/updateadvance")
    public ResponseEntity<String> UpdateAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.UpdateAdvance(advanceRequest);
    }
    @DeleteMapping(value = "/deleteadvance")
    public ResponseEntity<String> DeleteAdvance(@RequestParam String id){
        return advanceService.DeleteAvance(id);
    }
    @GetMapping(value = "/chargueadvances")
    public ResponseEntity<List<Avances>> chargueadvances(@RequestParam String id_route){
        return  advanceService.DeployAdvances(id_route);
    }
}
