package com.gilberto009199.anotai.desafio_backend.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gilberto009199.anotai.desafio_backend.api.request.CategoryRequest;
import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import com.gilberto009199.anotai.desafio_backend.aws.consumer.MessageQueueEnum;
import com.gilberto009199.anotai.desafio_backend.aws.services.SQSService;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;

import com.gilberto009199.anotai.desafio_backend.repositories.CategoryRepository;


@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final ObjectMapper objectMapper;
    private final SQSService sqsService;
    

    public CategoryService(CategoryRepository categoryRepository,
                          ObjectMapper objectMapper,
                          SQSService sqsService){

        this.repository = categoryRepository;
        this.objectMapper       = objectMapper;
        this.sqsService         = sqsService;
    }

    public List<CategoryResponse> getAll(){
        return repository.findAll()
        .stream()
        .map(entity -> new CategoryResponse(entity))
        .toList();
    }

    public CategoryResponse getById(String id){
        
        var entity = repository.findById(id);

        return new CategoryResponse(entity.get());

    }

    public CategoryResponse create(CategoryRequest data) {

        var entity = new CategoryEntity(data);

        entity = repository.insert(entity);

        // send SQS
        addInQueue(MessageQueueEnum.NEW_CATEGORY, entity);

        return new CategoryResponse(entity);
    }

    public CategoryResponse save(String id, CategoryRequest data) {

        // @todo add throw not found category
        var entity = repository.findById(id).get();

        entity
        .setTitle(data.getTitle())
        .setDescription(data.getDescription())
        .setOwnerId(data.getOwnerId());
        

        entity = repository.save(entity);

        // send SQS
        addInQueue(MessageQueueEnum.UPDATE_CATEGORY, entity);

        return new CategoryResponse(entity);
        
    }

    public void delete(String id) {
        
        var entity = repository.findById(id).get();

        repository.deleteById(id);

        addInQueue(MessageQueueEnum.REMOVE_CATEGORY, entity);

    }

    private void addInQueue(MessageQueueEnum processType, CategoryEntity entity){

        try{
            
            var payload = objectMapper.writeValueAsString(entity);

            sqsService.sendMessageAsync(processType, payload);

        }catch(Exception e){}

    }
}

