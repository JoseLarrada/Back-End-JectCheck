package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.RateRequest;
import com.Jetcheck.Aplication.Mapper.RateMapper;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.RateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateServices {
    private final RateRepository rateRepository;
    private final RateMapper mapper;
    private final AssignmentRepository assignmentRepository;

    public ResponseEntity<String> rateAssignment(RateRequest request) {
        if (assignmentRepository.existsById(request.getIdAssignment())) {
            rateRepository.save(mapper.rateMapper(request));
            return ResponseEntity.ok("Calificado Correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una entrega asociada");
        }
    }
    public ResponseEntity<String> updateRate(RateRequest request){
        if (rateRepository.existsById(request.getIdRate())){
            rateRepository.save(mapper.rateMapper(request));
            return ResponseEntity.ok("Actualizado Correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una calificacion asociada");
        }
    }
    public ResponseEntity<String> deleteRate(String idRate){
        if (rateRepository.existsById(idRate)){
            rateRepository.deleteById(idRate);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una calificacion asociada");
        }
    }
}
