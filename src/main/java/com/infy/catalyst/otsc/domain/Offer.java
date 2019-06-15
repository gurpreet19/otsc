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
 * A Offer.
 */

@Document(collection = "offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("name")
    private String name;

    @Field("description")
    private String description;

    @Field("isSellable")
    private Boolean isSellable;

    @Field("lifecycleStatus")
    private String lifecycleStatus;

    @Field("validFor_startDateTime")
    private String validFor_startDateTime;

    @Field("validFor_endDateTime")
    private String validFor_endDateTime;

    @Field("category")
    private String category;

    @Field("created_by")
    private String created_by;

    @Field("created_date")
    private ZonedDateTime created_date;

    @Field("type")
    private String type;

    @Field("template")
    private String template;

    @Field("basic_pricing_parameters")
    private Map<String,Object> basic_pricing_params = new HashMap<>();

    @Field("addon_pricing_parameters")
    private Map<String,Object> addon_pricing_params = new HashMap<>();

    @Field("offer_parameters")
    private Map<String,Object> offer_params = new HashMap<>();

    @Field("product_id")
    private String product_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Offer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdescription() {
        return description;
    }

    public Offer description(String description) {
        this.description = description;
        return this;
    }
    
    public void setdescription(String description) {
        this.description = description;
    }

    public Boolean getisSellable() {
        return isSellable;
    }

    public Offer isSellable(Boolean isSellable) {
        this.isSellable = isSellable;
        return this;
    }
    
    public void setisSellable(Boolean isSellable) {
        this.isSellable = isSellable;
    }
    
    public String getlifecycleStatus() {
        return lifecycleStatus;
    }

    public Offer lifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
        return this;
    }
    
    public void setlifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }
    
    public String getvalidFor_startDateTime() {
        return validFor_startDateTime;
    }

    public Offer validFor_startDateTime(String validFor_startDateTime) {
        this.validFor_startDateTime = validFor_startDateTime;
        return this;
    }
    
    public void setvalidFor_startDateTime(String validFor_startDateTime) {
        this.validFor_startDateTime = validFor_startDateTime;
    }
    
    public String getvalidFor_endDateTime() {
        return validFor_endDateTime;
    }

    public Offer validFor_endDateTime(String validFor_endDateTime) {
        this.validFor_endDateTime = validFor_endDateTime;
        return this;
    }
    
    public void setvalidFor_endDateTime(String validFor_endDateTime) {
        this.validFor_endDateTime = validFor_endDateTime;
    }

    public String getcategory() {
        return category;
    }

    public Offer category(String category) {
        this.category = category;
        return this;
    }
    
    public void setcategory(String category) {
        this.category = category;
    }
    
    public Map getBasic_pricing_params() {
        return basic_pricing_params;
    }

    public Offer basic_pricing_params(Map<String,Object> basic_pricing_params) {
        this.basic_pricing_params = basic_pricing_params;
        return this;
    }

    public void setBasic_pricing_params(Map<String,Object> basic_pricing_params) {
        this.basic_pricing_params = basic_pricing_params;
    }

    public Map getAddon_pricing_params() {
        return addon_pricing_params;
    }

    public Offer addon_pricing_params(Map<String,Object> addon_pricing_params) {
        this.addon_pricing_params = addon_pricing_params;
        return this;
    }

    public void setAddon_pricing_params(Map<String,Object> addon_pricing_params) {
        this.addon_pricing_params = addon_pricing_params;
    }

    public Map getOffer_params() {
        return offer_params;
    }

    public Offer offer_params(Map<String,Object> offer_params) {
        this.offer_params = offer_params;
        return this;
    }

    public void setOffer_params(Map<String,Object> offer_params) {
        this.offer_params = offer_params;
    }

    public String getProduct_id() {
        return product_id;
    }

    public Offer product_id(String product_id) {
        this.product_id = product_id;
        return this;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Offer created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public Offer created_date(ZonedDateTime created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public String getType() {
        return type;
    }

    public Offer type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Offer template(String template) {
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
        Offer offer = (Offer) o;
        if (offer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, offer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Offer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", type='" + type + "'" +
            ", template='" + template + "'" +
            ", basic_pricing_parameters='" + basic_pricing_params + "'" +
            ", addon_pricing_parameters='" + addon_pricing_params + "'" +
            '}';
    }
}
