package com.gilberto009199.anotai.desafio_backend.entities;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "product")
public class ProductEntity {

    @Id
    private String id;
    private String title;
    private String description;
    private BigDecimal price;
    
    private String categoryId;
    private String ownerId;

    public String getId() { return id;  }
    public ProductEntity setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() { return title; }
    public ProductEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() { return description;  }
    public ProductEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() { return price; }
    public ProductEntity setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getCategoryId() { return categoryId; }
    public ProductEntity setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getOwnerId() { return ownerId; }
    public ProductEntity setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
