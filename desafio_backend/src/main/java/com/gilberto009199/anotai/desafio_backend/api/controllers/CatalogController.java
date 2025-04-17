package com.gilberto009199.anotai.desafio_backend.api.controllers;

import com.gilberto009199.anotai.desafio_backend.aws.consumer.CatalogJSON;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gilberto009199.anotai.desafio_backend.aws.services.S3Service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RestController
@RequestMapping("/")
public class CatalogController {

    private final S3Service s3Service;
    public CatalogController(S3Service s3Service){
        this.s3Service = s3Service;
    }

    @Operation(summary = "get all Catalogs")
    @ApiResponse(responseCode = "200", description = "Catalogs")
    @GetMapping
    public ResponseEntity<List<CatalogJSON>> list() {

        var listCatalogs = s3Service.loadAllCatalogs();

        return ResponseEntity.ok().body(listCatalogs);
    }
    


}
