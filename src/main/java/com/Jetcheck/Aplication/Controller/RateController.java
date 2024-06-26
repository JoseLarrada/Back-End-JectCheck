package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Request.RateRequest;
import com.Jetcheck.Aplication.Entity.Calificaciones;
import com.Jetcheck.Aplication.Services.RateServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rate")
public class RateController {
    private final RateServices rateServices;
    @PostMapping()
    public ResponseEntity<String> rateAssignment(@RequestBody RateRequest request){
        return rateServices.rateAssignment(request);
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateRate(@RequestBody RateRequest request){
        return rateServices.updateRate(request);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRate(@PathVariable String id){
        return rateServices.deleteRate(id);
    }
    @GetMapping("/getRate/{id}")
    public ResponseEntity<Calificaciones> getInfo(@PathVariable String id){
        return rateServices.getRateById(id);
    }
}
