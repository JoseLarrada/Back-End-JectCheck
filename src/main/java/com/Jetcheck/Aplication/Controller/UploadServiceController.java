package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.Entity.Rutas;
import com.Jetcheck.Aplication.Entity.Usuarios;
import com.Jetcheck.Aplication.Services.UploadContentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/PrincipalContent")
public class UploadServiceController {
    private final UploadContentService uploadContentService;
    @GetMapping()
    public ResponseEntity<List<Rutas>> Upload(HttpServletRequest request){
        if (uploadContentService.UploadProjects(request)!=null){
            return ResponseEntity.ok(uploadContentService.UploadProjects(request));
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/profile")
    public ResponseEntity<Usuarios> LoadProfile(HttpServletRequest request){
        return uploadContentService.UploadProfile(request);
    }
}
