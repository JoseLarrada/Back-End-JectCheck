package com.Jetcheck.Aplication.Services;

import com.Jetcheck.Aplication.DTo.Asset;
import com.Jetcheck.Aplication.DTo.FileResponse;
import com.Jetcheck.Aplication.Entity.DatosArchivos;
import com.Jetcheck.Aplication.Repository.AssignmentRepository;
import com.Jetcheck.Aplication.Repository.FileAssingmentRepository;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service

public class S3Services {
    private final static String BUCKET="jectcheckbucket";
     @Autowired
    private AmazonS3Client s3Client;
    protected FileResponse putObject(MultipartFile file){
        String extension= StringUtils.getFilenameExtension(file.getOriginalFilename());
        String key=String.format("%s.%s", UUID.randomUUID(),extension);

        ObjectMetadata objectMetadata=new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        try{
            PutObjectRequest putObjectRequest=new PutObjectRequest(BUCKET,key,file.getInputStream(),objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicReadWrite);
            s3Client.putObject(putObjectRequest);
            var results=FileResponse.builder()
                    .key(key)
                    .url(getObjectUrl(key))
                    .fileName(file.getOriginalFilename())
                    .build();
            return results;
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage(),ex.getCause());
        }
    }
    public Asset GetObject(String key){
        S3Object s3Object=s3Client.getObject(BUCKET,key);
        ObjectMetadata metadata=s3Object.getObjectMetadata();

        try{
            S3ObjectInputStream inputStream=s3Object.getObjectContent();
            byte[] bytes= IOUtils.toByteArray(inputStream);
            return new Asset(bytes,metadata.getContentType());
        }catch (IOException ex){
            throw new RuntimeException(ex.getMessage(), ex.getCause());
        }
    }
    public void deleteObject(String key){
        s3Client.deleteObject(BUCKET,key);
    }
    public String getObjectUrl(String key){
        return String.format("https://%s.s3.amazonaws.com/%s",BUCKET,key);
    }
}
