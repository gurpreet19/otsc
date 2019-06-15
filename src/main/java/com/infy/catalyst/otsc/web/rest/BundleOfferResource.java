package com.infy.catalyst.otsc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.infy.catalyst.otsc.domain.BundleOffer;
import com.infy.catalyst.otsc.security.SecurityUtils;
import com.infy.catalyst.otsc.service.BundleOfferService;
import com.infy.catalyst.otsc.web.rest.util.HeaderUtil;
import com.infy.catalyst.otsc.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Bundle Offer.
 */
@RestController
@RequestMapping("/api")
public class BundleOfferResource {

    private final Logger log = LoggerFactory.getLogger(BundleOfferResource.class);

    private static final String ENTITY_NAME = "bundle_offer";
        
    private final BundleOfferService bundleOfferService;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public BundleOfferResource(BundleOfferService bundleOfferService) {
        this.bundleOfferService = bundleOfferService;
    }

    /**
     * POST  /bundle-offers : Create a new Bundle offer.
     *
     * @param offer the Bundle offer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new offer, or with status 400 (Bad Request) if the offer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bundle-offers")
    @Timed
    public ResponseEntity<BundleOffer> createBundleOffer(@Valid @RequestBody BundleOffer bundleOffer) throws URISyntaxException {
        log.debug("REST request to save Offer : {}", bundleOffer);
        if (bundleOffer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bundle offer cannot already have an ID")).body(null);
        }
        bundleOffer.setCreated_by(SecurityUtils.getCurrentUserLogin());
        BundleOffer result = bundleOfferService.save(bundleOffer);   
       // producerTemplate.sendBody("direct:offerToBESRoute", offer);
        
        return ResponseEntity.created(new URI("/api/bundle-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bundle-offers : Updates an existing bundle offer.
     *
     * @param offer the bundle offer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bundle offer,
     * or with status 400 (Bad Request) if the offer is not valid,
     * or with status 500 (Internal Server Error) if the bundle offer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bundle-offers")
    @Timed
    public ResponseEntity<BundleOffer> updateBundleOffer(@Valid @RequestBody BundleOffer bundleOffer) throws URISyntaxException {
        log.debug("REST request to update Bundle Offer : {}", bundleOffer);
        if (bundleOffer.getId() == null) {
            return createBundleOffer(bundleOffer);
        }
        BundleOffer result = bundleOfferService.save(bundleOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bundleOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bundle-offers : get all the bundle offers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of offers in body
     */
    @GetMapping("/bundle-offers")
    @Timed
    public ResponseEntity<List<BundleOffer>> getAllBundleOffers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Bundle Offers");
        Page<BundleOffer> page = bundleOfferService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bundle-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bundle-offers/:id : get the "id" bundle offer.
     *
     * @param id the id of the bundle offer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bundle offer, or with status 404 (Not Found)
     */
    @GetMapping("/bundle-offers/{id}")
    @Timed
    public ResponseEntity<BundleOffer> getBundleOffer(@PathVariable String id) {
        log.debug("REST request to get Bundle Offer : {}", id);
        BundleOffer bundleOffer = bundleOfferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bundleOffer));
    }

    /**
     * DELETE  /bundle-offers/:id : delete the "id" bundle offer.
     *
     * @param id the id of the bundle offer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bundle-offers/{id}")
    @Timed
    public ResponseEntity<Void> deleteBundleOffer(@PathVariable String id) {
        log.debug("REST request to delete Bundle Offer : {}", id);
        bundleOfferService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
