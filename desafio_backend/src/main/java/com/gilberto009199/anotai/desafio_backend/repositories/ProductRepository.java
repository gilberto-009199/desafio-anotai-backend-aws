package com.gilberto009199.anotai.desafio_backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.gilberto009199.anotai.desafio_backend.entities.ProductEntity;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String>{}
