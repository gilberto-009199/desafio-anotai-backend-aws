package com.gilberto009199.anotai.desafio_backend.api.request;

public class CategoryRequest {

    private String id;
    private String title;
    private String description;
    private String ownerId;

    public String getId() { return id;  }
    public CategoryRequest setId(String id) {
        this.id = id;
        return this;
    }

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
