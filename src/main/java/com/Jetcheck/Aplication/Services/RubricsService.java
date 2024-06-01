package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Entity.Rubricas;
import com.Jetcheck.Aplication.Repository.AdvanceRepository;
import com.Jetcheck.Aplication.Repository.RubricsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RubricsService {
    private final AdvanceRepository advanceRepository;
    private final S3Services s3Services;
    private final RubricsRepository rubricsRepository;

    public ResponseEntity<FileResponse> uploadRubric(MultipartFile file,String idAdvance){
        FileResponse response = s3Services.putObject(file);
        if (advanceRepository.existsById(idAdvance) && response!=null){
            var rubric= Rubricas.builder()
                    .key(response.getKey()).url(response.getUrl()).fileName(response.getFileName())
                    .idAvance(idAdvance)
                    .build();
            rubricsRepository.save(rubric);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.noContent().build();
        }
    }

    public ResponseEntity<String> deleteRubric(String key){
        if (rubricsRepository.existsById(key)){
            rubricsRepository.deleteById(key);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else{
            return ResponseEntity.badRequest().body("No se encontro la key");
        }
    }
    public ResponseEntity<List<Rubricas>> getRubricsByIdAdvance(String id){
        return ResponseEntity.ok(rubricsRepository.findAllByIdAvance(id));
    }
}
