package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.AdvanceRequest;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Excepcetion.PersonExceptions;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvanceService {
    private final RoutesRepository routesRepository;
    private final IdGeneratorConfig Generate;
    private final AdvanceRepository advanceRepository;
    private final OtherRepository advance;

    public ResponseEntity<String> CreateAdvance(AdvanceRequest request){
        if (!routesRepository.existsById(request.getRoute_id())){
            throw new PersonExceptions("Ruta inexistente");
        }
        saveorupdate(request,Generate.IdGenerator());
        return ResponseEntity.ok("Creado Correctamente");
    }
    public ResponseEntity<String> UpdateAdvance(AdvanceRequest request){
        if (!advanceRepository.existsById(request.getAdvance_id())){
            return ResponseEntity.badRequest().body("No existe el avance");
        }
        if (advance.Getstatebyadvance(request.getAdvance_id())==3){
            return ResponseEntity.badRequest().body("El avance ya ha sido calificado");
        }
        saveorupdate(request, request.getAdvance_id());
        return ResponseEntity.ok("Modificado Correctamente");
    }
    public ResponseEntity<String> DeleteAvance(String id_Advance){
        if (advanceRepository.existsById(id_Advance)){
            advanceRepository.deleteById(id_Advance);
            return ResponseEntity.ok("Eliminado Correctamente");
        }
        return ResponseEntity.notFound().build();
    }
    private void saveorupdate(AdvanceRequest request,String id){
        var advance= Avances.builder()
                .id_avance(id)
                .id_estado(5)
                .fecha_creacion(new Date())
                .id_ruta(request.getRoute_id())
                .descripcion(request.getDescription())
                .rubrica(request.getRubric())
                .titulo(request.getTittle())
                .build();
        advanceRepository.save(advance);
    }
    public ResponseEntity<List<Avances>> DeployAdvances(String id_Route){
        return ResponseEntity.ok(advance.getAvancesByRoutes(id_Route));
    }
}
