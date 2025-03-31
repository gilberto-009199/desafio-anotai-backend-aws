package com.gilberto009199.anotai.desafio_backend.api.response;

import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;

public class CategoryResponse {

    private String id;
    private String title;
    private String description;
    private String ownerId;

    public CategoryResponse() {}

    public CategoryResponse(CategoryEntity entity) {
        this
        .setId(entity.getId())
        .setTitle(entity.getTitle())
        .setDescription(entity.getDescription())
        .setOwnerId(entity.getOwnerId());
    }
    

    public String getId() { return id;  }
    public CategoryResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() { return title; }
    public CategoryResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description; }
    public CategoryResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOwnerId() { return ownerId;  }
    public CategoryResponse setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

}
