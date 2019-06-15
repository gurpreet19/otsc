package com.infy.catalyst.otsc.service;

import com.infy.catalyst.otsc.domain.Offer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Offer.
 */
public interface OfferService {

    /**
     * Save a offer.
     *
     * @param offer the entity to save
     * @return the persisted entity
     */
    Offer save(Offer offer);

    /**
     * Update an existing offer.
     *
     * @param offer the entity to update
     * @return the persisted entity
     */
	Offer update(Offer offer);
	
    /**
     *  Get all the offers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Offer> findAll(Pageable pageable);

    /**
     *  Get the "id" offer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Offer findOne(String id);

    /**
     *  Delete the "id" offer.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
