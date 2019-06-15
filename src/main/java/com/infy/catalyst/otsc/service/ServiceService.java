package com.infy.catalyst.otsc.service;

import com.infy.catalyst.otsc.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Service.
 */
public interface ServiceService {

    /**
     * Save a service.
     *
     * @param service the entity to save
     * @return the persisted entity
     */
	Service save(Service service);

    /**
     *  Get all the service.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Service> findAll(Pageable pageable);

    /**
     *  Get the "id" service.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Service findOne(String id);

    /**
     *  Delete the "id" service.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
