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
 * A Service.
 */

@Document(collection = "service")
public class Service implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
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

    @Field("externalId")
    private String externalId;
    
    @Field("service_parameters")
    private Map<String,Object> service_params = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Service name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getdescription() {
        return description;
    }

    public Service description(String description) {
        this.description = description;
        return this;
    }
    
    public void setdescription(String description) {
        this.description = description;
    }

    public String getlifecycleStatus() {
        return lifecycleStatus;
    }

    public Service lifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
        return this;
    }
    
    public void setlifecycleStatus(String lifecycleStatus) {
        this.lifecycleStatus = lifecycleStatus;
    }

    public Map getService_params() {
        return service_params;
    }

    public Service service_params(Map<String,Object> service_params) {
        this.service_params = service_params;
        return this;
    }

    public void setService_params(Map<String,Object> service_params) {
        this.service_params = service_params;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Service created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public ZonedDateTime getCreated_date() {
        return created_date;
    }

    public Service created_date(ZonedDateTime created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(ZonedDateTime created_date) {
        this.created_date = created_date;
    }

    public String getType() {
        return type;
    }

    public Service type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Service template(String template) {
        this.template = template;
        return this;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExternalId() {
        return externalId;
    }

    public Service externalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Service service = (Service) o;
        if (service.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Service{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", type='" + type + "'" +
            ", template='" + template + "'" +
            ", externalId='" + externalId + "'" +
            ", service_parameters='" + service_params + "'" +
            '}';
    }
}
