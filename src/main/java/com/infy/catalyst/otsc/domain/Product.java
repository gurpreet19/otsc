package com.infy.catalyst.otsc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

/**
 * A Product.
 */

@Document(collection = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max =50)
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("lifecycleStatus")
    private String lifecycleStatus;

    @Field("created_by")
    private String created_by;

    @Field("created_date")
    private ZonedDateTime created_date;

    @Field("type")
    private String type;

    @Field("template")
    private String template;

    @Field("product_parameters")
    private Map<String,Object> product_params = new HashMap<>();

    @Field("service_id")
    private String service_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public String getlifecycleStatus() {
        return lifecycleStatus;
    }

    public void setlifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public String getName() {
        return name;
    }

    public Product name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getProduct_params() {
        return product_params;
    }

    public Product product_params(Map<String,Object> product_params) {
        this.product_params = product_params;
        return this;
    }

    public void setProduct_params(Map<String,Object> product_params) {
        this.product_params = product_params;
    }

    public String getService_id() {
        return service_id;
    }

    public Product service_id(String service_id) {
        this.service_id = service_id;
        return this;
    }

    public void setProduct_id(String service_id) {
        this.service_id = service_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Product created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public Product created_date(ZonedDateTime created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public String getType() {
        return type;
    }

    public Product type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Product template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        if (product.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Product{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", type='" + type + "'" +
            ", template='" + template + "'" +
            ", product_parameters='" + product_params + "'" +
            '}';
    }
}
