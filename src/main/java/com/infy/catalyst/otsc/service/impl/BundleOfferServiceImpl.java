package com.infy.catalyst.otsc.service.impl;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.infy.catalyst.otsc.domain.BundleOffer;
import com.infy.catalyst.otsc.domain.Sequence;
import com.infy.catalyst.otsc.repository.BundleOfferRepository;
import com.infy.catalyst.otsc.repository.SequenceRepository;
import com.infy.catalyst.otsc.service.BundleOfferService;


/**
 * Service Implementation for managing Bundle Offer.
 */
@Service
public class BundleOfferServiceImpl implements BundleOfferService{

    private final Logger log = LoggerFactory.getLogger(BundleOfferServiceImpl.class);
    
    private final BundleOfferRepository bundleOfferRepository;
    private final SequenceRepository sequenceRepository;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public BundleOfferServiceImpl(BundleOfferRepository bundleOfferRepository, SequenceRepository sequenceRepository) {
        this.bundleOfferRepository = bundleOfferRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Save a Bundle offer.
     *
     * @param bundleOffer the entity to save
     * @return the persisted entity
     */
    @Override
    public BundleOffer save(BundleOffer bundleOffer) {
    	Long incrementedSequence;
    	log.debug("Request to save Bundle Offer : {}", bundleOffer);
        
        //create the BES compatible ID for bundle Offer
        Sequence seq =  sequenceRepository.findOne("bundleOfferid");
        
        if (seq != null) {
        	incrementedSequence = seq.getSeq()+ 1L;
        	seq.setSeq(incrementedSequence);
        } else {
        	incrementedSequence = 1L;
        	seq =  new Sequence();
        	seq.setId("bundleOfferid");
        	seq.setSeq(incrementedSequence);        	
        }        
        
        //save the id     	
    	sequenceRepository.save(seq);
    	
    	bundleOffer.setId(incrementedSequence.toString());  
    	BundleOffer result = bundleOfferRepository.save(bundleOffer);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:offerToBESRoute", result);
        return result;
    }

    /**
     *  Get all the Bundle offers.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<BundleOffer> findAll(Pageable pageable) {
        log.debug("Request to get all Bundle Offers");
        Page<BundleOffer> result = bundleOfferRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one Bundle offer by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public BundleOffer findOne(String id) {
        log.debug("Request to get Bundle Offer : {}", id);
        BundleOffer bundleOffer = bundleOfferRepository.findOne(id);
        return bundleOffer;
    }

    /**
     *  Delete the Bundle offer by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Bundle Offer : {}", id);
        bundleOfferRepository.delete(id);
    }
    
   
}
