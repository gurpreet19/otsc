package com.infy.catalyst.otsc.service.impl;

import com.infy.catalyst.otsc.service.ResourceService;
import com.infy.catalyst.otsc.domain.Resource;
import com.infy.catalyst.otsc.repository.ResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Resource.
 */
@Service
public class ResourceServiceImpl implements ResourceService{

    private final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);
    
    private final ResourceRepository resourceRepository;

    public ResourceServiceImpl(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }

    /**
     * Save a resource.
     *
     * @param resource the entity to save
     * @return the persisted entity
     */
    @Override
    public Resource save(Resource resource) {
        log.debug("Request to save Resource : {}", resource);
        Resource result = resourceRepository.save(resource);
        return result;
    }

    /**
     *  Get all the resources.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Resource> findAll(Pageable pageable) {
        log.debug("Request to get all Resources");
        Page<Resource> result = resourceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one resource by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Resource findOne(String id) {
        log.debug("Request to get Resource : {}", id);
        Resource resource = resourceRepository.findOne(id);
        return resource;
    }

    /**
     *  Delete the  resource by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Resource : {}", id);
        resourceRepository.delete(id);
    }
}
