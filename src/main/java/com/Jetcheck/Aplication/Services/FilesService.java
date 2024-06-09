package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.Response.FileResponse;
import com.Jetcheck.Aplication.Entity.DatosArchivos;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.FileAssingmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilesService {
    private final FileAssingmentRepository fileRepository;
    private final AssignmentRepository assignmentRepository;
    private final S3Services s3Services;
    public FileResponse uploadFiles(MultipartFile file, String idAssignment){
        if (assignmentRepository.existsById(idAssignment)){
            FileResponse response= s3Services.putObject(file);
            var archivo= DatosArchivos.builder()
                    .key(response.getKey())
                    .url(response.getUrl())
                    .fileName(response.getFileName())
                    .idEntrega(idAssignment)
                    .build();
            fileRepository.save(archivo);
            return response;
        }
        throw new RuntimeException();
    }
    public ResponseEntity<String> deleteFile(String key){
        if (fileRepository.existsById(key)){
            fileRepository.deleteById(key);
            return ResponseEntity.ok("Eliminado Correctamente");
        }else {
            return ResponseEntity.badRequest().body("No se encontro un archivo");
        }
    }
    public ResponseEntity<List<DatosArchivos>> getFilesById(String idAssingment){
        return ResponseEntity.ok(fileRepository.findAllByIdEntrega(idAssingment));
    }
}
