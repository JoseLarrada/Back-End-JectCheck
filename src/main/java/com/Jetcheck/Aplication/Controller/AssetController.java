package com.Jetcheck.Aplication.Controller;

import com.Jetcheck.Aplication.DTo.Asset;
import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Services.S3Services;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/assets")
@RequiredArgsConstructor
public class AssetController {
    @Autowired
    private S3Services s3Services;

    @PostMapping(value = "/Upload")
    public FileResponse upload(@RequestParam("file") MultipartFile File){
        return s3Services.putObject(File);
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
}
