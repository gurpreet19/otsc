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
import com.infy.catalyst.otsc.domain.Product;
import com.infy.catalyst.otsc.service.ProductService;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for publishing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductPublish {

    private final Logger log = LoggerFactory.getLogger(ProductPublish.class);
   
    private final ProductService productService;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public ProductPublish(ProductService productService) {
        this.productService = productService;
    }
    
    /**
     * POST  /product/:id : Publish a product with id.
     *
     * @param id the id of the product to publish
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publish/product/{id}")
    @Timed
    public ResponseEntity<Product> publishProduct(@PathVariable String id) {
        log.debug("REST request to publish Product : {}", id);
        Product product = productService.findOne(id);
        producerTemplate.sendBody("direct:productToTMFProductSpecificationRoute", product);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(product));
    }
}
