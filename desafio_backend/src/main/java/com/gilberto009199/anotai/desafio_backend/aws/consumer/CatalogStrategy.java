package com.gilberto009199.anotai.desafio_backend.aws.consumer;

import java.util.Iterator;

import com.gilberto009199.anotai.desafio_backend.api.response.CategoryResponse;
import com.gilberto009199.anotai.desafio_backend.api.response.ProductResponse;
import com.gilberto009199.anotai.desafio_backend.aws.services.S3Service;
import com.gilberto009199.anotai.desafio_backend.entities.CategoryEntity;
import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;

public class CatalogStrategy {

    // load (menssage.payload().ownerId()+".json")
    // alter

    public static void process(S3Service s3, MessageQueue<?> message){
        
        switch (message.processType()) {
            case NEW_PRODUCT, UPDATE_PRODUCT, REMOVE_PRODUCT -> processProduct(s3, message);
            case NEW_CATEGORY, UPDATE_CATEGORY, REMOVE_CATEGORY -> processCategory(s3, message);
            default -> throw new IllegalArgumentException("Tipo de mensagem não reconhecido: " + message);
        }

    }

    // Process Product
    private static void processProduct(S3Service s3, MessageQueue<?> message) {
        
        var product = (MessageQueue<ProductEntity>) message;

        switch (product.processType()) {
            case NEW_PRODUCT    ->  processNewProduct(s3, product);
            case UPDATE_PRODUCT ->  processUpdateProduct(s3, product);
            case REMOVE_PRODUCT ->  processRemoveProduct(s3, product);
        }
    }
    
    private static void processNewProduct(S3Service s3, MessageQueue<ProductEntity> message) {
        System.out.println("processNewProduct");
        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());

        catalog.getProducts().add(new ProductResponse(entity));


        s3.saveCatalog(catalog);

    }

    private static void processUpdateProduct(S3Service s3, MessageQueue<ProductEntity> message) {
        System.out.println("processUpdateProduct");
        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());

        
        
        for (ProductResponse product : catalog.getProducts()) {
            if (product.getId().equals(entity.getId())) {
         
                product.setTitle(entity.getTitle());
                product.setDescription(entity.getDescription());
         
            }
        }


        s3.saveCatalog(catalog);
    }

    private static void processRemoveProduct(S3Service s3, MessageQueue<ProductEntity> message) {
        System.out.println("processRemoveProduct");
        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());

        
        catalog.getCategories().removeIf(product -> product.getId().equals(entity.getId()));


        s3.saveCatalog(catalog);
    }
    
    // Process Category
    private static void processCategory(S3Service s3, MessageQueue<?> message) {
        
        var category = (MessageQueue<CategoryEntity>) message;

        switch (category.processType()) {
            case NEW_CATEGORY -> processNewCategory(s3, category);
            case UPDATE_CATEGORY ->  processUpdateCategory(s3, category);
            case REMOVE_CATEGORY -> processRemoveCategory(s3, category);
        }
    }

    private static void processNewCategory(S3Service s3, MessageQueue<CategoryEntity> message) {
        System.out.println("processNewCategory");

        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());

        catalog.getCategories().add(new CategoryResponse(entity));

        s3.saveCatalog(catalog);
    }
    private static void processUpdateCategory(S3Service s3, MessageQueue<CategoryEntity> message) {
        System.out.println("processUpdateCategory");
        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());

        for (CategoryResponse category : catalog.getCategories()) {
            if (category.getId().equals(entity.getId())) {
                
                category.setTitle(entity.getTitle());
                category.setDescription(entity.getDescription());
                
            }
        }

        s3.saveCatalog(catalog);
    }
    private static void processRemoveCategory(S3Service s3, MessageQueue<CategoryEntity> message) {
        System.out.println("processRemoveCategory");
        var entity = message.payload();

        var catalog = s3.loadOrCreateCatalog(entity.getOwnerId());


        catalog.getCategories().removeIf(category -> category.getId().equals(entity.getId()));


        s3.saveCatalog(catalog);
    }
    



}
