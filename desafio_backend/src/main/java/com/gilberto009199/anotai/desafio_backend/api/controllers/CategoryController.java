package com.gilberto009199.anotai.desafio_backend.api.controllers;

import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import com.gilberto009199.anotai.desafio_backend.api.request.CategoryRequest;
import com.gilberto009199.anotai.desafio_backend.services.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;
    
    public CategoryController(CategoryService service){
        this.service = service;
    }

    @Operation(summary = "get all Categories")
    @ApiResponse(responseCode = "200", description = "Category list")
    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Create Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Category for id"),
            @ApiResponse(responseCode = "404", description = "Not found Category for id")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(
            @Parameter(description = "id of Category Get", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable
            String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @Operation(summary = "Create Category")
    @ApiResponse(responseCode = "201", description = "Created Category")
    @PostMapping
    public ResponseEntity<CategoryResponse> create(@RequestBody CategoryRequest data){

        var entity = service.create(data);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(entity);
    }

    @Operation(summary = "Update Category")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Updated Category"),
            @ApiResponse(responseCode = "404", description = "Not Found Category")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> save(
            @Parameter(description = "id of Category Update", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable
            String id,
            @RequestBody
            CategoryRequest data){

        var entity = service.save(id, data);

        return ResponseEntity.ok(entity);
    }

    @Operation(summary = "Delete Category")
    @ApiResponse(responseCode = "200", description = "Deleted Category")
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "id of Category Delete", example = "68010d4e4c2f3a28a69adce5")
            @PathVariable
            String id){
        service.delete(id);   
    }
}
