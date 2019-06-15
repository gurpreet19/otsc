package com.infy.catalyst.otsc.service;

import com.infy.catalyst.otsc.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Product.
 */
public interface ProductService {

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    Product save(Product product);

    /**
     * Update an existing product.
     *
     * @param product the entity to update
     * @return the persisted entity
     */
	Product update(Product product);

    /**
     *  Get all the products.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Product> findAll(Pageable pageable);

    /**
     *  Get the "id" product.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Product findOne(String id);

    /**
     *  Delete the "id" product.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
