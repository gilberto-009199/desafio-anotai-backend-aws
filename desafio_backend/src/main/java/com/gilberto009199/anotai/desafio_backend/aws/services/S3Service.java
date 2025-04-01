package com.gilberto009199.anotai.desafio_backend.aws.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.List;


import org.springframework.stereotype.Service;

import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;
import com.gilberto009199.anotai.desafio_backend.aws.consumer.CatalogJSON;

import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;

import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class S3Service {

    private final S3Client s3Client;
    private final String s3BucketName;
    private final ObjectMapper objectMapper;

    
    public S3Service(S3Client s3Client, String s3BucketName, ObjectMapper objectMapper) {
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
        this.objectMapper = objectMapper;
    }

    public List<CatalogJSON> loadAllCatalogs() {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(s3BucketName)
                .build();

        var listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);
        
        var catalogs = listObjectsResponse
        .contents()
        .stream()
        .filter(s3Object -> 
            s3Object.key()
            .endsWith(".json")
        ).map(s3Object -> 
            loadOrCreateCatalog( 
                s3Object.key()
                .replace(".json", "") 
            )
        ).toList();

        return catalogs;
    }
    
    public CatalogJSON loadOrCreateCatalog(String ownerId) {
        String fileName = ownerId + ".json";

        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(s3BucketName)
                .key(fileName)
                .build();

        try {

            ResponseInputStream<GetObjectResponse> s3Object = s3Client.getObject(getObjectRequest);


            String catalogJson = convertStreamToString(s3Object);


            return objectMapper.readValue(catalogJson, CatalogJSON.class);

        } catch (Exception e) {
            
            return new CatalogJSON()
                .setOwnerId(ownerId)
                .setProducts( new  ArrayList<ProductResponse>()  )
                .setCategories( new  ArrayList<CategoryResponse>() );
                
        }
    }

    public void saveCatalog(CatalogJSON catalog) {
        String fileName = catalog.getOwnerId() + ".json"; 

        try {

            String catalogJson = objectMapper.writeValueAsString(catalog);


            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3BucketName)
                    .key(fileName)
                    .build();

            // Envia os dados para o S3
            s3Client.putObject(putObjectRequest, RequestBody.fromString(catalogJson));

            System.out.println("Catalog saved successfully.");
        
        } catch (Exception e) {

            throw new RuntimeException("Erro ao salvar o catálogo no S3", e);
        
        }
    }
    
    private String convertStreamToString(ResponseInputStream<GetObjectResponse> s3Object) throws Exception {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(s3Object, StandardCharsets.UTF_8))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
    }
}