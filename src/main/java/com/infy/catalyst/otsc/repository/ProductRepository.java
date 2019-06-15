package com.infy.catalyst.otsc.repository;

import com.infy.catalyst.otsc.domain.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Product entity.
 */
@SuppressWarnings("unused")
public interface ProductRepository extends MongoRepository<Product,String> {

}
