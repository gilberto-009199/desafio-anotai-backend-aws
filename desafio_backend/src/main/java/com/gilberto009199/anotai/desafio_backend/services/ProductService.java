package com.gilberto009199.anotai.desafio_backend.services;

import org.springframework.stereotype.Service;

import com.gilberto009199.anotai.desafio_backend.api.request.ProductRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;

@Service
public class ProductService {

    public ProductResponse getById(String id) {
        return new ProductResponse().setId(id);
    }

    public ProductResponse[] getAll() {
        return new ProductResponse []{};
    }

    public Object save(String id, ProductRequest data) {
        return new ProductResponse()
        .setId(id)
        .setPrice(data.getPrice())
        .setTitle(data.getTitle())
        .setDescription(data.getDescription());
    }

    public Object create(ProductRequest data) {
        return new ProductResponse()
        .setId("12345")
        .setPrice(data.getPrice())
        .setTitle(data.getTitle())
        .setDescription(data.getDescription());

    }

    public void delete(String id) {
        
    }

}
