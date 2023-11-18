package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServices {
    private final AssignmentRepository assignmentRepository;
    private final AdvanceRepository advanceRepository;
    private final OtherRepository AssignmentJDBC;
    private final IdGeneratorConfig idGeneratorConfig;

    public ResponseEntity<String> AddAssignment(AssignmentRequest request){
        if (!advanceRepository.existsById(request.getId_advance())){
            return ResponseEntity.badRequest().body("No existe el Avance");
        }
        AddOrModify(request,idGeneratorConfig.IdGenerator());
        return ResponseEntity.ok("Entrega correcta");
    }
    public ResponseEntity<String> ModifyAssignment(AssignmentRequest request){
        if (!advanceRepository.existsById(request.getId_advance())){
            return ResponseEntity.badRequest().body("No existe el Avance");
        }
        if (AssignmentJDBC.Getstatebyadvance(request.getId_advance())==3){
            return ResponseEntity.badRequest().body("La entrega Ya fue calificada");
        }
        AddOrModify(request,request.getId_Assignment());
        return ResponseEntity.ok("Entrega Modificada");
    }
    public ResponseEntity<String> DeleteAssignment(String Id){
        if (assignmentRepository.existsById(Id)){
            assignmentRepository.deleteById(Id);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else{
            return ResponseEntity.badRequest().body("No se encontro Entrega");
        }

    }
    public ResponseEntity<String> RateAssignment(double Rate, String IdAssignment){
        if (assignmentRepository.existsById(IdAssignment)){
            AssignmentJDBC.rateprogress(Rate,IdAssignment);
            return ResponseEntity.ok("Calificado Correctamente");
        }else{
            return ResponseEntity.badRequest().body("No se Encontro el Id");
        }
    }
    public ResponseEntity<List<Entregas>> DeployAssignment(String id_advance){
        return ResponseEntity.ok(AssignmentJDBC.getAssigmentByAdvance(id_advance));
    }
    private void AddOrModify(AssignmentRequest request,String id){
        var delivery= Entregas.builder()
                .id_avance(request.getId_advance())
                .anexos(request.getAnnexes())
                .archivo_entrega(request.getFile())
                .comentario(request.getComment())
                .id_entrega(id)
                .build();
        assignmentRepository.save(delivery);
    }
}
