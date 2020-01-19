package com.home.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.home.domain.Product;
@RepositoryRestResource
public interface ProductRepository extends MongoRepository<Product, String>{

}
