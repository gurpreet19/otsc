package com.infy.catalyst.otsc.service;

import com.infy.catalyst.otsc.domain.BundleOffer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Offer.
 */
public interface BundleOfferService {

    /**
     * Save a Bundle offer.
     *
     * @param bundleOffer the entity to save
     * @return the persisted entity
     */
	BundleOffer save(BundleOffer bundleOffer);

    /**
     *  Get all the Bundle offers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<BundleOffer> findAll(Pageable pageable);

    /**
     *  Get the "id" Bundle offer.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    BundleOffer findOne(String id);

    /**
     *  Delete the "id" Bundle offer.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
