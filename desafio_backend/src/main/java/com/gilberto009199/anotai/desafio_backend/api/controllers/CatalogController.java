package com.gilberto009199.anotai.desafio_backend.api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gilberto009199.anotai.desafio_backend.aws.services.S3Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/")
public class CatalogController {

    private final S3Service s3Service;
    public CatalogController(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @GetMapping
    public ResponseEntity<?> list() {

        var listCatalogs = s3Service.loadAllCatalogs();

        return ResponseEntity.ok().body(listCatalogs);
    }
    


}
