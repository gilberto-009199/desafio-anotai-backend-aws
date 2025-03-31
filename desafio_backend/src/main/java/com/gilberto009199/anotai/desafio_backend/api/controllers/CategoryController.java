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
import com.gilberto009199.anotai.desafio_backend.services.CategoryService;


@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService service;
    
    public CategoryController(CategoryService service){
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<?> get(){
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CategoryRequest data){

        var entity = service.create(data);

        return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> save( @PathVariable String id,
                                   @RequestBody CategoryRequest data){

        var entity = service.save(id, data);

        return ResponseEntity.ok(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        
    }
}
