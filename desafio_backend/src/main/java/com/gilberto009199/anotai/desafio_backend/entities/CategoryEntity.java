package com.gilberto009199.anotai.desafio_backend.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.gilberto009199.anotai.desafio_backend.api.request.CategoryRequest;
import com.gilberto009199.anotai.desafio_backend.api.request.ProductRequest;


@Document(collection = "category")
public class CategoryEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private String ownerId;


    public CategoryEntity() {}
    public CategoryEntity(CategoryRequest data) {
        this
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setOwnerId(data.getOwnerId());
    }

    public String getId() { return id;  }
    public CategoryEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() { return title; }
    public CategoryEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description; }
    public CategoryEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOwnerId() { return ownerId;  }
    public CategoryEntity setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
