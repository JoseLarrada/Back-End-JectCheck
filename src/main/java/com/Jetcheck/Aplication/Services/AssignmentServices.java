package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.AssignmentRequest;
import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Entity.DatosArchivos;
import com.Jetcheck.Aplication.Entity.Entregas;
import com.Jetcheck.Aplication.Mapper.AssignmentMapper;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.FileAssingmentRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServices {
    private final AssignmentRepository assignmentRepository;
    private final AdvanceRepository advanceRepository;
    private final OtherRepository assignmentJDBC;
    private final IdGeneratorConfig idGeneratorConfig;
    private final AssignmentMapper assignmentMapper;

    public ResponseEntity<String> addAssignment(AssignmentRequest request){
        request.setIdAssignment(idGeneratorConfig.IdGenerator());
        if (request.getFile()==null){
            return ResponseEntity.badRequest().body("Ingrese un archivo valido");
        }
        if (!advanceRepository.existsById(request.getIdAdvance())){
            return ResponseEntity.badRequest().body("No existe el Avance");
        }else{
            assignmentRepository.save(assignmentMapper.mapperAssignment(request));
            return ResponseEntity.ok("Entrega correcta");
        }
    }
    public ResponseEntity<String> modifyAssignment(AssignmentRequest request){
        if (!advanceRepository.existsById(request.getIdAdvance())){
            return ResponseEntity.badRequest().body("No existe el Avance");
        }
        if (assignmentJDBC.Getstatebyadvance(request.getIdAdvance())==3){
            return ResponseEntity.badRequest().body("La entrega Ya fue calificada");
        }
        assignmentRepository.save(assignmentMapper.mapperAssignment(request));
        return ResponseEntity.ok("Entrega Modificada");
    }
    public ResponseEntity<String> deleteAssignment(String id){
        if (id==null){return ResponseEntity.badRequest().body("Ingrese un valor");}
        if (assignmentRepository.existsById(id)){
            assignmentRepository.deleteById(id);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else{
            return ResponseEntity.badRequest().body("No se encontro Entrega");
        }
    }
    public ResponseEntity<String> rateAssignment(double rate, String idAssignment){
        if (idAssignment==null){
            return ResponseEntity.badRequest().body("Ingrese un valor");
        }
        if (rate>5.0){
            return ResponseEntity.badRequest().body("La nota maxima es de 5.0");
        }else if (rate<0){
            return ResponseEntity.badRequest().body("La nota minma es de 0");
        }
        if (assignmentRepository.existsById(idAssignment)){
            assignmentJDBC.rateprogress(rate,idAssignment);
            return ResponseEntity.ok("Calificado Correctamente");
        }else{
            return ResponseEntity.badRequest().body("No se Encontro el Id");
        }
    }
    public ResponseEntity<List<Entregas>> deployAssignment(String id_advance){
        return ResponseEntity.ok(assignmentJDBC.getAssigmentByAdvance(id_advance));
    }


}
