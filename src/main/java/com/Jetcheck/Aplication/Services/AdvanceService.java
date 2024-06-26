package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.Config.IdGeneratorConfig;
import com.Jetcheck.Aplication.DTo.Request.AdvanceRequest;
import com.Jetcheck.Aplication.DTo.Response.FileResponse;
import com.Jetcheck.Aplication.Entity.Avances;
import com.Jetcheck.Aplication.Mapper.AdvanceMapper;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.OtherRepository;
import com.Jetcheck.Aplication.Repository.RoutesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvanceService {
    private final RoutesRepository routesRepository;
    private final IdGeneratorConfig Generate;
    private final AdvanceRepository advanceRepository;
    private final OtherRepository advance;
    private final AdvanceMapper advanceMapper;

    public ResponseEntity<String> createAdvance(AdvanceRequest request){
        if (request.getTittle()==null ){
            return ResponseEntity.badRequest().body("Añada un titulo al avance");
        }
        if (request.getDescription()==null){
            return ResponseEntity.badRequest().body("Añada una descripcion al avance");
        }
        if (!routesRepository.existsById(request.getRouteId())){
            return ResponseEntity.badRequest().body("Ruta inexistente");
        }else{
            request.setAdvanceId(Generate.IdGenerator());
            advanceRepository.save(advanceMapper.mapperAdvance(request));
            updateRubrics(request.getRubric(),request);
            return ResponseEntity.ok("Creado Correctamente");
        }
    }

    public ResponseEntity<String> updateAdvance(AdvanceRequest request){
        if (request.getTittle()==null ){
            return ResponseEntity.badRequest().body("Añada un titulo al proyecto");
        }
        if (request.getDescription()==null){
            return ResponseEntity.badRequest().body("Añada una descripcion al proyecto");
        }
        if (!advanceRepository.existsById(request.getAdvanceId())){
            return ResponseEntity.badRequest().body("No existe el avance");
        }
        if (advance.Getstatebyadvance(request.getAdvanceId())==3){
            return ResponseEntity.badRequest().body("El avance ya ha sido calificado");
        }
        advanceRepository.save(advanceMapper.mapperAdvance(request));
        updateRubrics(request.getRubric(),request);
        return ResponseEntity.ok("Modificado Correctamente");
    }
    @Transactional
    public ResponseEntity<String> deleteAvance(String titleAdvance,String idAdvance){
        if (titleAdvance==null){
            return ResponseEntity.badRequest().body("Añada un id valido");
        }
        Avances response= advanceRepository.findById(idAdvance).orElse(null);
        assert response != null;
        if (titleAdvance.equals(response.getTitulo())){
            advanceRepository.deleteById(idAdvance);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else {
            return ResponseEntity.badRequest().body("No coinciden los nombres");
        }
    }

    public ResponseEntity<List<Avances>> deployAdvances(String id_Route){
        return ResponseEntity.ok(advance.getAvancesByRoutes(id_Route));
    }
    private void updateRubrics(List<FileResponse> responseList,AdvanceRequest request){
        for (var item: responseList){
            advance.updateIdByKeyUrl("rubricas","id_avance","id_rubrica",
                    request.getAdvanceId(), item.getKey());
        }
    }
}
