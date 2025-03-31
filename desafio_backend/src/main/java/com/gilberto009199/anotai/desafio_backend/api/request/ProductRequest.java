package com.gilberto009199.anotai.desafio_backend.api.request;

import java.math.BigDecimal;

public class ProductRequest {

    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    private CategoryRequest category;
    private String ownerId;

    public String getId() { return id;  }
    public ProductRequest setId(String id) {
        this.id = id;
        return this;
    }

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
