package com.awsDemo.controller;

import com.awsDemo.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {
    @Autowired
    private StorageService service;

    //for upload the file on AWSs3Console
    @PostMapping("/upload")
    public ResponseEntity<Object> UploadFile(@RequestParam(value = "file")MultipartFile file){
        return new ResponseEntity<>(service.uploadFile(file), HttpStatus.OK);

    }
    //download the file from s3Console
      @GetMapping("/download/{fileName}")
        public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName){
        byte[] data=service.downloadFile(fileName);
        ByteArrayResource resource=new ByteArrayResource(data);
          return ResponseEntity
                  .ok()
                  .contentLength(data.length)
                  .header("Content-type", "application/octet-stream")
                  .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                  .body(resource);

        }
        //delete the filr from s3Console

        @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName){
        return new ResponseEntity<>(service.deleteFile(fileName),HttpStatus.OK);
        }
    }

