package com.infy.catalyst.otsc.repository;

import com.infy.catalyst.otsc.domain.Offer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Offer entity.
 */
@SuppressWarnings("unused")
public interface OfferRepository extends MongoRepository<Offer,String> {

}
