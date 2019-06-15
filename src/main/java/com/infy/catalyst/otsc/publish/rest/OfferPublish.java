package com.infy.catalyst.otsc.publish.rest;

import java.net.URISyntaxException;
import java.util.Optional;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.infy.catalyst.otsc.domain.Offer;
import com.infy.catalyst.otsc.service.OfferService;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for publishing Offer.
 */
@RestController
@RequestMapping("/api")
public class OfferPublish {

    private final Logger log = LoggerFactory.getLogger(OfferPublish.class);
   
    private final OfferService offerService;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public OfferPublish(OfferService offerService) {
        this.offerService = offerService;
    }
    
    /**
     * POST  /offer/:id : Publish a Offer with id.
     *
     * @param id the id of the offer to publish
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publish/offer/{id}")
    @Timed
    public ResponseEntity<Offer> publishOffer(@PathVariable String id) {
        log.debug("REST request to publish Offer : {}", id);
        Offer offer = offerService.findOne(id);
        producerTemplate.sendBody("direct:offerToTMFProductOfferingPriceRoute", offer);
        producerTemplate.sendBody("direct:offerToTMFProductOfferingRoute", offer);        
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(offer));
    }
}
