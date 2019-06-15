package com.infy.catalyst.otsc.service;

import com.infy.catalyst.otsc.domain.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Resource.
 */
public interface ResourceService {

    /**
     * Save a resource.
     *
     * @param resource the entity to save
     * @return the persisted entity
     */
    Resource save(Resource resource);

    /**
     *  Get all the resource.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Resource> findAll(Pageable pageable);

    /**
     *  Get the "id" resource.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Resource findOne(String id);

    /**
     *  Delete the "id" resource.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
