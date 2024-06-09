package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Response.FileResponse;
import com.Jetcheck.Aplication.Entity.Rubricas;
import com.Jetcheck.Aplication.Services.RubricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rubrics")
public class RubricController {
    private final RubricsService rubricsService;
    @PostMapping("/Upload")
    public ResponseEntity<FileResponse> uploadRubric(@RequestParam("file") MultipartFile file, @RequestParam("id") String id){
        return rubricsService.uploadRubric(file,id);
    }
    @DeleteMapping("/deleteKey/{key}")
    public ResponseEntity<String> deleteRubric(@PathVariable String key){
        return rubricsService.deleteRubric(key);
    }
    @GetMapping("/getRubrics/{id}")
    public ResponseEntity<List<Rubricas>> getRubricsByIdAdvance(@PathVariable String id){
        return rubricsService.getRubricsByIdAdvance(id);
    }
}
