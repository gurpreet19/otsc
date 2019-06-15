package com.infy.catalyst.otsc.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.infy.catalyst.otsc.service.TemplateService;
import com.infy.catalyst.otsc.service.TemplateService.Template;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Template.
 */
@RestController
@RequestMapping("/api")
public class TemplateResource {

    private final Logger log = LoggerFactory.getLogger(TemplateResource.class);

    private static final String ENTITY_NAME = "template";
        
    private final TemplateService templateService;

    public TemplateResource(TemplateService templateService) {
        this.templateService = templateService;
    }

    /**
     * GET  /templates/offers : get all the offer templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offer templates in body
     */
    @GetMapping("/templates/offers")
    @Timed
    public ResponseEntity<List<Template>> getOfferTemplates(HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get templates");
        List<Template> templates = templateService.getOfferTemplates(request.getServletContext().getRealPath(""));
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    /**
     * GET  /templates/resources : get all the resource templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of offer templates in body
     */
    @GetMapping("/templates/resources")
    @Timed
    public ResponseEntity<List<Template>> getResourceTemplates(HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get templates");
        List<Template> templates = templateService.getResourceTemplates(request.getServletContext().getRealPath(""));
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    /**
     * GET  /templates : get all the product templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of product templates in body
     */
    @GetMapping("/templates/products")
    @Timed
    public ResponseEntity<List<Template>> getProductTemplates(HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get product templates");
        List<Template> templates = templateService.getProductTemplates(request.getServletContext().getRealPath(""));
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    /**
     * GET  /templates : get all the service templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of product templates in body
     */
    @GetMapping("/templates/services")
    @Timed
    public ResponseEntity<List<Template>> getServiceTemplates(HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get product templates");
        List<Template> templates = templateService.getServiceTemplates(request.getServletContext().getRealPath(""));
        return new ResponseEntity<>(templates, HttpStatus.OK);
    }

    /**
     * GET  /templates : get all the service templates.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of product templates in body
     */
    @GetMapping("/templates/bundle-offers")
    @Timed
    public ResponseEntity<List<Template>> getBundleOfferTemplates(HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get bundle offer templates");
        List<Template> templates = templateService.getBundleOfferTemplates(request.getServletContext().getRealPath(""));
        return new ResponseEntity<>(templates, HttpStatus.OK);
    } 
}