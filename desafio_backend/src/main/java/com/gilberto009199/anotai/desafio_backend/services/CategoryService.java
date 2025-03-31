package com.gilberto009199.anotai.desafio_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gilberto009199.anotai.desafio_backend.api.request.CategoryRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.repositories.CategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository){
        this.repository = repository;
    }

    public List<CategoryResponse> getAll(){
        return repository.findAll()
        .stream()
        .map(entity -> new CategoryResponse(entity))
        .toList();
    }

    public CategoryResponse getById(String id){
        
        var entity = repository.findById(id);

        return new CategoryResponse(entity.get());

    }

    public CategoryResponse create(CategoryRequest data) {

        var entity = new CategoryEntity(data);

        entity = repository.insert(entity);

        return new CategoryResponse(entity);
    }

    public CategoryResponse save(String id, CategoryRequest data) {

        // @todo add throw not found category
        var entity = repository.findById(id).get();

        entity
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setOwnerId(data.getOwnerId());
        

        repository.save(entity);

        return new CategoryResponse(entity);
        
    }

    public void delete(String id) {
        repository.deleteById(id);
    }
}

