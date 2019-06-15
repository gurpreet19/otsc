package com.infy.catalyst.otsc.service.impl;

import com.infy.catalyst.otsc.service.ProductService;
import com.infy.catalyst.otsc.domain.Product;
import com.infy.catalyst.otsc.domain.Sequence;
import com.infy.catalyst.otsc.repository.ProductRepository;
import com.infy.catalyst.otsc.repository.SequenceRepository;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service Implementation for managing Product.
 */
@Service
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);
    
    private final ProductRepository productRepository;
    private final SequenceRepository sequenceRepository;
    
    @Autowired
    ProducerTemplate producerTemplate;

    public ProductServiceImpl(ProductRepository productRepository, SequenceRepository sequenceRepository) {
        this.productRepository = productRepository;
        this.sequenceRepository = sequenceRepository;
    }

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    @Override
    public Product save(Product product) {
    	Long incrementedSequence;
        log.debug("Request to save Product : {}", product);
        
        
      //create the BES compatible ID for offer
        Sequence seq =  sequenceRepository.findOne("productid");

        if (seq != null) {
        	incrementedSequence = seq.getSeq()+ 1L;
        	seq.setSeq(incrementedSequence);
        } else {
        	incrementedSequence = 1L;
        	seq =  new Sequence();
        	seq.setId("productid");
        	seq.setSeq(incrementedSequence);        	
        }
        
        //save the id
    	sequenceRepository.save(seq);
    	
    	product.setId(incrementedSequence.toString());  
    	Product result = productRepository.save(product);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:productToBESRoute", result);
        return result;
    }

    /**
     * Update an existing product.
     *
     * @param product the entity to update
     * @return the persisted entity
     */
    @Override
    public Product update(Product product) {
        log.debug("Request to update Product : {}", product);
        
    	Product result = productRepository.save(product);
       
        //result will now have the id....from DB
        //producerTemplate.sendBody("direct:productToBESRoute", result);
        return result;
    }

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Product> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        Page<Product> result = productRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Product findOne(String id) {
        log.debug("Request to get Product : {}", id);
        Product product = productRepository.findOne(id);
        return product;
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }
}
