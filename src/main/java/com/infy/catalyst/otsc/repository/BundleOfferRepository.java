package com.infy.catalyst.otsc.repository;

import com.infy.catalyst.otsc.domain.BundleOffer;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Bundle Offer entity.
 */
@SuppressWarnings("unused")
public interface BundleOfferRepository extends MongoRepository<BundleOffer,String> {

}
