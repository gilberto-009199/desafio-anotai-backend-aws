package com.gilberto009199.anotai.desafio_backend.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gilberto009199.anotai.desafio_backend.api.request.ProductRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;
import com.gilberto009199.anotai.desafio_backend.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service){
        this.service = service;
    }

    @Operation(summary = "Get all product ")
    @ApiResponse(responseCode = "200", description = "Get All Product")
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get by id product ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Get Product for id"),
            @ApiResponse(responseCode = "404", description = "Not Found Product")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getById(
            @Parameter(description = "id of Product Get", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable("id")
            String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create product ")
    @ApiResponse(responseCode = "201", description = "Created Product")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest data){

        var entity = service.create(data);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(entity);
    }

    @Operation(summary = "Create product ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated Product"),
            @ApiResponse(responseCode = "404", description = "Not Found Product")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> save(
            @Parameter(description = "id of Product Update", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable
            String id,
            @RequestBody
            ProductRequest data){

        var entity = service.save(id, data);

        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "Deleted product ")
    @ApiResponse(responseCode = "200", description = "Deleted Product")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "id of Product Delete", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable String id){
        service.delete(id);
    }
}
