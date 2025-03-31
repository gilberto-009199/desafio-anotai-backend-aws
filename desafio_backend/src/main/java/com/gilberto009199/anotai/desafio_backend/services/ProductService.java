package com.gilberto009199.anotai.desafio_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gilberto009199.anotai.desafio_backend.api.request.ProductRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;
import com.gilberto009199.anotai.desafio_backend.repositories.CategoryRepository;
import com.gilberto009199.anotai.desafio_backend.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse getById(String id) {

        // @todo return Throw ProductNotFound
        var entity = productRepository.findById(id).get();

        var category = categoryRepository.findById(entity.getCategoryId());

        if(category.isEmpty())return new ProductResponse(entity);

        return new ProductResponse(entity, category.get());

    }

    public List<ProductResponse> getAll() {

        var listEntity = productRepository.findAll();

        return listEntity
        .stream()
        .map(entity -> new ProductResponse(entity))
        .toList();
    }

    public ProductResponse save(String id, ProductRequest data) {
        
        // @todo thow user not found
        var entity = productRepository.findById(id).get();

        entity
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setPrice(data.getPrice())
        .setOwnerId(data.getOwnerId());

        Optional<CategoryEntity> category = Optional.empty();

        // @todo exception category for Id no exist
        if(data.getCategory() != null) category = categoryRepository.findById(data.getCategory().getId());
        
        
        if(!category.isPresent())
            entity.setCategoryId(null);
        else
            entity.setCategoryId(category.get().getId());
        
        productRepository.save(entity);

        if(category.isPresent())
            return new ProductResponse(entity, category.get());
        else
            return new ProductResponse(entity);
    }

    public ProductResponse create(ProductRequest data) {

        var entity = new ProductEntity(data);

        Optional<CategoryEntity> category = Optional.empty();

        // @todo exception category for Id no exist
        if(entity.getCategoryId() != null) category = categoryRepository.findById(entity.getCategoryId());
        
        
        if(!category.isPresent())entity.setCategoryId(null);
        
        entity = productRepository.insert(entity);

        
        if(category.isPresent())
            return new ProductResponse(entity, category.get());
        else
            return new ProductResponse(entity);

    }

    public void delete(String id) {
        productRepository.deleteById(id);
    }

}
