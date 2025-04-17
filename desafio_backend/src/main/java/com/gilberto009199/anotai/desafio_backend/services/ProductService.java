package com.gilberto009199.anotai.desafio_backend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gilberto009199.anotai.desafio_backend.api.request.ProductRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;
import com.gilberto009199.anotai.desafio_backend.aws.consumer.MessageQueueEnum;
import com.gilberto009199.anotai.desafio_backend.aws.services.SQSService;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;
import com.gilberto009199.anotai.desafio_backend.repositories.CategoryRepository;
import com.gilberto009199.anotai.desafio_backend.repositories.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ObjectMapper objectMapper;
    private final SQSService sqsService;
    

    public ProductService(ProductRepository productRepository,
                          CategoryRepository categoryRepository,
                          ObjectMapper objectMapper,
                          SQSService sqsService){
        this.productRepository  = productRepository;
        this.categoryRepository = categoryRepository;
        this.objectMapper       = objectMapper;
        this.sqsService         = sqsService;
    }

    public ProductResponse getById(String id) {

        // @todo return Throw ProductNotFound
        var entity = productRepository.findById(id).get();

        Optional<CategoryEntity> category =  Optional.empty();

        if(entity.getCategoryId() != null)category = categoryRepository.findById(entity.getCategoryId());

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
        if(data.getCategory() != null) category = categoryRepository.findById(id);
        
        
        if(!category.isPresent())
            entity.setCategoryId(null);
        else
            entity.setCategoryId(category.get().getId());
        
        entity = productRepository.save(entity);

        // send menssage SQS
        addInQueue(MessageQueueEnum.UPDATE_PRODUCT, entity);

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

        // send menssage SQS
        addInQueue(MessageQueueEnum.NEW_PRODUCT, entity);

        if(category.isPresent())
            return new ProductResponse(entity, category.get());
        else
            return new ProductResponse(entity);

    }

    public void delete(String id) {
        
        var entity = productRepository.findById(id).get();

        productRepository.deleteById(id);

        addInQueue(MessageQueueEnum.REMOVE_PRODUCT, entity);
    }

    private void addInQueue(MessageQueueEnum processType, ProductEntity entity){

        try{
            
            var payload = objectMapper.writeValueAsString(entity);

            sqsService.sendMessageAsync(processType, payload);

        }catch(Exception e){}

    }

}
