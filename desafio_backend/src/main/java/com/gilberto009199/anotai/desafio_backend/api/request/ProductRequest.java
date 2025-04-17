package com.gilberto009199.anotai.desafio_backend.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "Product data")
public class ProductRequest {

    @Schema(description = "Product title")
    @NotBlank
    private String title;

    @Schema(description = "Product description")
    @NotBlank
    private String description;

    @Schema(description = "Product price, min = 1")
    @Size(min = 1)
    private BigDecimal price;

    @Schema(description = "Product category", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private CategoryRequest category;

    @Schema(description = "Product owner id")
    @NotBlank
    private String ownerId;

    public String getTitle() { return title; }
    public ProductRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description;  }
    public ProductRequest setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() { return price; }
    public ProductRequest setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public CategoryRequest getCategory() { return category; }
    public ProductRequest setCategory(CategoryRequest category) {
        this.category = category;
        return this;
    }

    public String getOwnerId() { return ownerId; }
    public ProductRequest setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

}
