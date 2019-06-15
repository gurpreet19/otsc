package com.infy.catalyst.otsc.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.HashMap;
import java.util.Map;

/**
 * A Resource.
 */

@Document(collection = "resource")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 50)
    @Field("name")
    private String name;

    @Field("created_by")
    private String created_by;

    @Field("created_date")
    private LocalDate created_date;

    @Field("type")
    private String type;

    @Field("template")
    private String template;

    @Field("resource_parameters")
    private Map<String,String> resource_params = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Resource name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map getResource_params() {
        return resource_params;
    }

    public Resource resource_params(Map<String,String> resource_params) {
        this.resource_params = resource_params;
        return this;
    }

    public void setResource_params(Map<String,String> resource_params) {
        this.resource_params = resource_params;
    }

    public String getCreated_by() {
        return created_by;
    }

    public Resource created_by(String created_by) {
        this.created_by = created_by;
        return this;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public LocalDate getCreated_date() {
        return created_date;
    }

    public Resource created_date(LocalDate created_date) {
        this.created_date = created_date;
        return this;
    }

    public void setCreated_date(LocalDate created_date) {
        this.created_date = created_date;
    }

    public String getType() {
        return type;
    }

    public Resource type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public Resource template(String template) {
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
        Resource resource = (Resource) o;
        if (resource.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, resource.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Resource{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_by='" + created_by + "'" +
            ", created_date='" + created_date + "'" +
            ", type='" + type + "'" +
            ", template='" + template + "'" +
            ", resource_parameters='" + resource_params + "'" +
            '}';
    }
}
