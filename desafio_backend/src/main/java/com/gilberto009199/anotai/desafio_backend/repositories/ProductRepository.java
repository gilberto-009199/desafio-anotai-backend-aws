package com.gilberto009199.anotai.desafio_backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;

public interface ProductRepository extends MongoRepository<ProductEntity, String>{

}
