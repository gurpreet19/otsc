package com.infy.catalyst.otsc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.infy.catalyst.otsc.domain.Service;
import com.infy.catalyst.otsc.security.SecurityUtils;
import com.infy.catalyst.otsc.service.ServiceService;
import com.infy.catalyst.otsc.web.rest.util.HeaderUtil;
import com.infy.catalyst.otsc.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Service.
 */
@RestController
@RequestMapping("/api")
public class ServiceResource {

    private final Logger log = LoggerFactory.getLogger(ServiceResource.class);

    private static final String ENTITY_NAME = "service";
        
    private final ServiceService serviceService;

    public ServiceResource(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    /**
     * POST  /services : Create a new service.
     *
     * @param service the service to create
     * @return the ResponseEntity with status 201 (Created) and with body the new service, or with status 400 (Bad Request) if the service has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/services")
    @Timed
    public ResponseEntity<Service> createService(@Valid @RequestBody Service service) throws URISyntaxException {
        log.debug("REST request to save Service : {}", service);
        if (service.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new service cannot already have an ID")).body(null);
        }
        service.setCreated_by(SecurityUtils.getCurrentUserLogin());
        Service result = serviceService.save(service);
        return ResponseEntity.created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /services : Updates an existing service.
     *
     * @param service the service to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated service,
     * or with status 400 (Bad Request) if the service is not valid,
     * or with status 500 (Internal Server Error) if the service couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/services")
    @Timed
    public ResponseEntity<Service> updateService(@Valid @RequestBody Service service) throws URISyntaxException {
        log.debug("REST request to update Service : {}", service);
        if (service.getId() == null) {
            return createService(service);
        }
        Service result = serviceService.save(service);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, service.getId().toString()))
            .body(result);
    }

    /**
     * GET  /services : get all the services.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of services in body
     */
    @GetMapping("/services")
    @Timed
    public ResponseEntity<List<Service>> getAllServices(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Services");
        Page<Service> page = serviceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/services");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /services/:id : get the "id" service.
     *
     * @param id the id of the service to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the service, or with status 404 (Not Found)
     */
    @GetMapping("/services/{id}")
    @Timed
    public ResponseEntity<Service> getService(@PathVariable String id) {
        log.debug("REST request to get Service : {}", id);
        Service service = serviceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(service));
    }

    /**
     * DELETE  /services/:id : delete the "id" service.
     *
     * @param id the id of the service to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/services/{id}")
    @Timed
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        log.debug("REST request to delete Service : {}", id);
        serviceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
