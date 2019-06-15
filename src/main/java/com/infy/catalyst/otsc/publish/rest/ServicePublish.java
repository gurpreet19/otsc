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
import com.infy.catalyst.otsc.domain.Service;
import com.infy.catalyst.otsc.service.ServiceService;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for publishing Service.
 */
@RestController
@RequestMapping("/api")
public class ServicePublish {

    private final Logger log = LoggerFactory.getLogger(ServicePublish.class);
   
    private final ServiceService serviceService;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public ServicePublish(ServiceService serviceService) {
        this.serviceService = serviceService;
    }
    
    /**
     * POST  /service/:id : Publish a service with id.
     *
     * @param id the id of the service to publish
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/publish/service/{id}")
    @Timed
    public ResponseEntity<Service> publishService(@PathVariable String id) {
        log.debug("REST request to publish Service : {}", id);
        Service service = serviceService.findOne(id);
        producerTemplate.sendBody("direct:serviceToTMFServiceSpecificationRoute", service);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(service));
    }
}
