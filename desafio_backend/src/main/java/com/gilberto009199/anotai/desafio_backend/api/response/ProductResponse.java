package com.gilberto009199.anotai.desafio_backend.api.response;

import java.math.BigDecimal;

import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;

public class ProductResponse {

    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private CategoryResponse category;
    private String ownerId;

    public ProductResponse() {}

    public ProductResponse(ProductEntity entity) {
        this
        .setId(entity.getId())
        .setTitle(entity.getTitle())
        .setDescription(entity.getDescription())
        .setOwnerId(entity.getOwnerId())
        .setPrice(entity.getPrice());

        if(entity.getCategoryId() != null) setCategory(
            new CategoryResponse()
            .setId( entity.getCategoryId() )
        );

    }

    public ProductResponse(ProductEntity entity, CategoryEntity categoryEntity) {
        this
        .setId(entity.getId())
        .setTitle(entity.getTitle())
        .setDescription(entity.getDescription())
        .setOwnerId(entity.getOwnerId())
        .setPrice(entity.getPrice())
        .setCategory(
            new CategoryResponse(categoryEntity)
        );
    }

    public String getId() { return id;  }
    public ProductResponse setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() { return title; }
    public ProductResponse setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description;  }
    public ProductResponse setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() { return price; }
    public ProductResponse setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CategoryResponse getCategory() { return category; }
    public ProductResponse setCategory(CategoryResponse category) {
        this.category = category;
        return this;
    }

    public String getOwnerId() { return ownerId; }
    public ProductResponse setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

}
