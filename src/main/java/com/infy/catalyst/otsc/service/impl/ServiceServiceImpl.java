package com.infy.catalyst.otsc.service.impl;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infy.catalyst.otsc.domain.Sequence;
import com.infy.catalyst.otsc.repository.SequenceRepository;
import com.infy.catalyst.otsc.repository.ServiceRepository;
import com.infy.catalyst.otsc.service.ServiceService;


/**
 * Service Implementation for managing Service.
 */
@Service
public class ServiceServiceImpl implements ServiceService{

    private final Logger log = LoggerFactory.getLogger(ServiceServiceImpl.class);
    
    private final ServiceRepository serviceRepository;
    private final SequenceRepository sequenceRepository;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public ServiceServiceImpl(ServiceRepository serviceRepository, SequenceRepository sequenceRepository) {
        this.serviceRepository = serviceRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Save a Service.
     *
     * @param service the entity to save
     * @return the persisted entity
     */
    @Override
    public com.infy.catalyst.otsc.domain.Service save(com.infy.catalyst.otsc.domain.Service service) {
    	Long incrementedSequence;
        log.debug("Request to save Service : {}", service);
        
        //create the BES compatible ID for Service
        Sequence seq =  sequenceRepository.findOne("serviceid");
        
        if (seq != null) {
        	incrementedSequence = seq.getSeq()+ 1L;
        	seq.setSeq(incrementedSequence);
        } else {
        	incrementedSequence = 1L;
        	seq =  new Sequence();
        	seq.setId("serviceid");
        	seq.setSeq(incrementedSequence);        	
        }
        
        //save the id 
    	sequenceRepository.save(seq);
    	
    	service.setId(incrementedSequence.toString());  
    	com.infy.catalyst.otsc.domain.Service result = serviceRepository.save(service);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:serviceToBESRoute", result);
        return result;
    }

    /**
     *  Get all the services.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<com.infy.catalyst.otsc.domain.Service> findAll(Pageable pageable) {
        log.debug("Request to get all Services");
        Page<com.infy.catalyst.otsc.domain.Service> result = serviceRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one Service by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public com.infy.catalyst.otsc.domain.Service findOne(String id) {
        log.debug("Request to get Service : {}", id);
        com.infy.catalyst.otsc.domain.Service service = serviceRepository.findOne(id);
        return service;
    }

    /**
     *  Delete the Service by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Service : {}", id);
        serviceRepository.delete(id);
    }
    
   
}
