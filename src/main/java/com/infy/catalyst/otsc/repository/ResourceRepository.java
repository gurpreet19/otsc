package com.infy.catalyst.otsc.repository;

import com.infy.catalyst.otsc.domain.Resource;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Resource entity.
 */
@SuppressWarnings("unused")
public interface ResourceRepository extends MongoRepository<Resource,String> {

}
