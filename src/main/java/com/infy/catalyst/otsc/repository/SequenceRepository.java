package com.infy.catalyst.otsc.repository;


import com.infy.catalyst.otsc.domain.Sequence;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Offer entity.
 */
@SuppressWarnings("unused")
public interface SequenceRepository extends MongoRepository<Sequence,String> {
	
}
