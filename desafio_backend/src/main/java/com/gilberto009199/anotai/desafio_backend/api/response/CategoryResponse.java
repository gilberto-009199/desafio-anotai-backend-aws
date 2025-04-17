package com.gilberto009199.anotai.desafio_backend.api.response;

import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Category Data")
public class CategoryResponse {

    @Schema(description = "Category id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "Category title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Category description", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Category owner id", requiredMode = Schema.RequiredMode.REQUIRED)
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
