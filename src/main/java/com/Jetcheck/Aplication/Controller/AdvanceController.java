package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Request.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Repository.OtherRepository;
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
    private final OtherRepository otherRepository;
    @PostMapping(value = "/createadvance")
    public ResponseEntity<String> createAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.createAdvance(advanceRequest);
    }

    @PutMapping(value = "/updateadvance")
    public ResponseEntity<String> updateAdvance(@RequestBody AdvanceRequest advanceRequest){
        return advanceService.updateAdvance(advanceRequest);
    }
    @DeleteMapping(value = "/deleteadvance/{title}/{id}")
    public ResponseEntity<String> deleteAdvance(@PathVariable String title,@PathVariable String id){
        return advanceService.deleteAvance(title,id);
    }
    @GetMapping(value = "/chargueadvances/{id_route}")
    public ResponseEntity<List<Avances>> chargueAdvances(@PathVariable String id_route){
        return  advanceService.deployAdvances(id_route);
    }
}
