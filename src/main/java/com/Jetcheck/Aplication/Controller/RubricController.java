package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Services.RubricsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
