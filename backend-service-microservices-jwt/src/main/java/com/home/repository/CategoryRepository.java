package com.home.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.home.domain.Category;
@RepositoryRestResource
public interface CategoryRepository extends MongoRepository<Category, String>{

}
