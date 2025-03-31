package com.gilberto009199.anotai.desafio_backend.api.controllers;

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
import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @GetMapping
    public ResponseEntity<?> list(){
        return ResponseEntity.ok(new CategoryResponse[]{});
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategory(@PathVariable String id){
        return ResponseEntity.ok(new CategoryResponse());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequest data){
        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(new CategoryResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save( @PathVariable String id,
                                   @RequestBody CategoryRequest data){
        return ResponseEntity.ok(new CategoryResponse());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        
    }
}
