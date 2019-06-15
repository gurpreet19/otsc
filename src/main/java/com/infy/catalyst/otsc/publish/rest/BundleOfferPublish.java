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
import com.infy.catalyst.otsc.domain.BundleOffer;
import com.infy.catalyst.otsc.service.BundleOfferService;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for publishing Bundle Offer.
 */
@RestController
@RequestMapping("/api")
public class BundleOfferPublish {

    private final Logger log = LoggerFactory.getLogger(BundleOfferPublish.class);
   
    private final BundleOfferService bundleOfferService;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public BundleOfferPublish(BundleOfferService bundleOfferService) {
        this.bundleOfferService = bundleOfferService;
    }
    
    /**
     * POST  /bundle-offer/:id : Publish a Bundle offer with id.
     *
     * @param id the id of the Bundle offer to publish
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publish/bundle-offer/{id}")
    @Timed
    public ResponseEntity<BundleOffer> publishBundleOffer(@PathVariable String id) {
        log.debug("REST request to publish Bundle Offer : {}", id);
        BundleOffer bundleOffer = bundleOfferService.findOne(id);
        log.debug("Publish Bundle Offer : {}", bundleOffer);
        producerTemplate.sendBody("direct:offerToTMFBundleProductOfferingRoute", bundleOffer);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bundleOffer));
    }
}
