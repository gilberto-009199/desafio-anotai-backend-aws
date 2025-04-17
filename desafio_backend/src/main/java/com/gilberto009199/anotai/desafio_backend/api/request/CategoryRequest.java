package com.gilberto009199.anotai.desafio_backend.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Category Data")
public class CategoryRequest {

    @Schema(description = "Category title")
    @NotBlank
    private String title;

    @Schema(description = "Category description")
    @NotBlank
    private String description;

    @Schema(description = "Category owner id")
    @NotBlank
    private String ownerId;

    public String getTitle() { return title; }
    public CategoryRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description; }
    public CategoryRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getOwnerId() { return ownerId;  }
    public CategoryRequest setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

}
