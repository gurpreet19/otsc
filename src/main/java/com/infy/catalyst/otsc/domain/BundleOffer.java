package com.infy.catalyst.otsc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Bundle Offer.
 */

@Document(collection = "bundle_offer")
public class BundleOffer implements Serializable {

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
    
    @Field("created_by")
    private String created_by;

    @Field("created_date")
    private ZonedDateTime created_date;
    
    @Field("type")
    private String type;

    @Field("template")
    private String template;
    
    @Field("offer_ids")
    List<String> offer_ids = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public BundleOffer name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdescription() {
        return description;
    }

    public BundleOffer description(String description) {
        this.description = description;
        return this;
    }
    
    public void setdescription(String description) {
        this.description = description;
    }

    public Boolean getisSellable() {
        return isSellable;
    }

    public BundleOffer isSellable(Boolean isSellable) {
        this.isSellable = isSellable;
        return this;
    }
    
    public void setisSellable(Boolean isSellable) {
        this.isSellable = isSellable;
    }
    
    public String getlifecycleStatus() {
        return lifecycleStatus;
    }

    public BundleOffer lifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
        return this;
    }
    
    public void setlifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }
    
    public String getvalidFor_startDateTime() {
        return validFor_startDateTime;
    }

    public BundleOffer validFor_startDateTime(String validFor_startDateTime) {
        this.validFor_startDateTime = validFor_startDateTime;
        return this;
    }
    
    public void setvalidFor_startDateTime(String validFor_startDateTime) {
        this.validFor_startDateTime = validFor_startDateTime;
    }
    
    public String getvalidFor_endDateTime() {
        return validFor_endDateTime;
    }

    public BundleOffer validFor_endDateTime(String validFor_endDateTime) {
        this.validFor_endDateTime = validFor_endDateTime;
        return this;
    }
    
    public void setvalidFor_endDateTime(String validFor_endDateTime) {
        this.validFor_endDateTime = validFor_endDateTime;
    }
    
    public String getCreated_by() {
        return created_by;
    }

    public BundleOffer created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public BundleOffer created_date(ZonedDateTime created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public String getType() {
        return type;
    }

    public BundleOffer type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public BundleOffer template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List getOffer_ids() {
        return offer_ids;
    }

    public BundleOffer offer_ids(List<String> offer_ids) {
        this.offer_ids = offer_ids;
        return this;
    }

    public void setOffer_ids(List<String> offer_ids) {
        this.offer_ids = offer_ids;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BundleOffer offer = (BundleOffer) o;
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
        return "BundleOffer{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", offer_ids='" + offer_ids + "'" +
            '}';
    }
}
