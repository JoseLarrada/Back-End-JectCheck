package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Asset;
import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Entity.DatosArchivos;
import com.Jetcheck.Aplication.Services.AssignmentServices;
import com.Jetcheck.Aplication.Services.FilesService;
import com.Jetcheck.Aplication.Services.S3Services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {
    @Autowired
    private S3Services s3Services;
    @Autowired
    private FilesService filesService;

    @PostMapping(value = "/Upload")
    public FileResponse upload(@RequestParam("file") MultipartFile File,@RequestParam("id") String id){
        return filesService.uploadFiles(File,id);
    }
    @GetMapping(value = "Get-Object", params = "key")
    ResponseEntity<ByteArrayResource>getObject(@RequestParam String key){
        Asset asset=s3Services.GetObject(key);
        ByteArrayResource resource=new ByteArrayResource(asset.getContent());
        return ResponseEntity.ok()
                .header("Content-Type", asset.getContentType())
                .contentLength(asset.getContent().length)
                .body(resource);
    }
    @DeleteMapping(value = "delete-object", params = "key")
    void deleteObject(@RequestParam String key){
        s3Services.deleteObject(key);
    }
    @DeleteMapping("/deleteKey/{key}")
    public ResponseEntity<String> deleteKey(@PathVariable String key){
        return filesService.deleteFile(key);
    }
    @GetMapping("/getFiles/{id}")
    public ResponseEntity<List<DatosArchivos>> getAll(@PathVariable String id){
        return filesService.getFilesById(id);
    }
}
