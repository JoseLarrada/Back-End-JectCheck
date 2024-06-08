package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.RateRequest;
import com.Jetcheck.Aplication.Entity.Calificaciones;
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
            if (notaValidation(request.getRateValue())!=null){
                return notaValidation(request.getRateValue());
            }
            rateRepository.save(mapper.rateMapper(request));
            return ResponseEntity.ok("Calificado Correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una entrega asociada");
        }
    }
    public ResponseEntity<String> updateRate(RateRequest request){
        Calificaciones response=rateRepository.findByIdEntrega(request.getIdAssignment()).orElse(null);
        if (response != null){
            if (notaValidation(request.getRateValue())!=null){
                return notaValidation(request.getRateValue());
            }
            request.setIdRate(response.getIdCalificacion());
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
    private ResponseEntity<String>notaValidation(Double rateValue){
        if (rateValue>5.0){
            return ResponseEntity.badRequest().body("Supera la nota maxima que es de 5.0");
        }else if(rateValue<0){
            return ResponseEntity.badRequest().body("Nota por debajo de la nota minima que es 0.0");
        }else {
            return null;
        }
    }
    public ResponseEntity<Calificaciones> getRateById(String id){
        Calificaciones response=rateRepository.findByIdEntrega(id).orElse(null);
        if (response!=null){
            return ResponseEntity.ok(response);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
