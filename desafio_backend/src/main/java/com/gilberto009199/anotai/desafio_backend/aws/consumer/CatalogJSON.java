package com.gilberto009199.anotai.desafio_backend.aws.consumer;

import java.util.List;

import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;

public class CatalogJSON{
    
    private String ownerId;
    private List<ProductResponse> products;
    private List<CategoryResponse> categories;

    public String getOwnerId() { return ownerId;  }
    public CatalogJSON setOwnerId(String ownerId) {
        this.ownerId = ownerId;
        return this;
    }

    public List<ProductResponse> getProducts() { return products;  }
    public CatalogJSON setProducts(List<ProductResponse> products) {
        this.products = products;
        return this;
    }

    public List<CategoryResponse> getCategories() { return categories;  }
    public CatalogJSON setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
        return this;
    }

    
    
}
