package com.infy.catalyst.otsc.service.impl;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.domain.Sequence;
import com.infy.catalyst.otsc.repository.OfferRepository;
import com.infy.catalyst.otsc.repository.SequenceRepository;
import com.infy.catalyst.otsc.service.OfferService;


/**
 * Service Implementation for managing Offer.
 */
@Service
public class OfferServiceImpl implements OfferService{

    private final Logger log = LoggerFactory.getLogger(OfferServiceImpl.class);
    
    private final OfferRepository offerRepository;
    private final SequenceRepository sequenceRepository;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public OfferServiceImpl(OfferRepository offerRepository, SequenceRepository sequenceRepository) {
        this.offerRepository = offerRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Save a offer.
     *
     * @param offer the entity to save
     * @return the persisted entity
     */
    @Override
    public Offer save(Offer offer) {
    	Long incrementedSequence;
    	log.debug("Request to save Offer : {}", offer);
        
        //create the BES compatible ID for offer
        Sequence seq =  sequenceRepository.findOne("offerid");
        
        if (seq != null) {
        	incrementedSequence = seq.getSeq()+ 1L;
        	seq.setSeq(incrementedSequence);
        } else {
        	incrementedSequence = 1L;
        	seq =  new Sequence();
        	seq.setId("offerid");
        	seq.setSeq(incrementedSequence);        	
        }        
        
        //save the id     	
    	sequenceRepository.save(seq);
    	
        offer.setId(incrementedSequence.toString());  
        Offer result = offerRepository.save(offer);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:offerToBESRoute", result);
        return result;
    }

    /**
     * Update an existing offer.
     *
     * @param offer the entity to update
     * @return the persisted entity
     */
    @Override
    public Offer update(Offer offer) {  
        Offer result = offerRepository.save(offer);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:offerToBESRoute", result);
        return result;
    }
    
    /**
     *  Get all the offers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Offer> findAll(Pageable pageable) {
        log.debug("Request to get all Offers");
        Page<Offer> result = offerRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one offer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Offer findOne(String id) {
        log.debug("Request to get Offer : {}", id);
        Offer offer = offerRepository.findOne(id);
        return offer;
    }

    /**
     *  Delete the  offer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Offer : {}", id);
        offerRepository.delete(id);
    }
    
   
}
