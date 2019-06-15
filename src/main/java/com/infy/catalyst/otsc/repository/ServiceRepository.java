package com.infy.catalyst.otsc.repository;

import com.infy.catalyst.otsc.domain.Service;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Service entity.
 */
@SuppressWarnings("unused")
public interface ServiceRepository extends MongoRepository<Service,String> {

}
