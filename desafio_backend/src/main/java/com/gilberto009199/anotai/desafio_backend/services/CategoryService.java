package com.gilberto009199.anotai.desafio_backend.services;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.gilberto009199.anotai.desafio_backend.api.request.CategoryRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;

@Service
public class CategoryService {


    public CategoryResponse[] getAll(){
        return new CategoryResponse[]{};
    }

    public CategoryResponse getById(String id){
        return new CategoryResponse()
        .setId(id);
    }

    public CategoryResponse create(CategoryRequest data) {
        return new CategoryResponse()
        .setId("123")
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setOwnerId(data.getOwnerId());
    }

    public CategoryResponse save(String id, CategoryRequest data) {
        return new CategoryResponse()
        .setId(id)
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setOwnerId(data.getOwnerId());
    }

    public void delete(String id) {
        
    }
}

