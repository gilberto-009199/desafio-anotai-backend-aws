package com.gilberto009199.anotai.desafio_backend.api.response;

import java.math.BigDecimal;

import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Product Data")
public class ProductResponse {

    @Schema(description = "Product id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "Product Title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;

    @Schema(description = "Product Title", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Product Price", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal price;

    @Schema(description = "Product Category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private CategoryResponse category;

    @Schema(description = "Product Owner id", requiredMode = Schema.RequiredMode.REQUIRED)
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
