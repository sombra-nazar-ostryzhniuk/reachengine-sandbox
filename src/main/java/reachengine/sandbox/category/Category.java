package reachengine.sandbox.category;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

public class Category {

    private Long id;
    private String name;
    private String description;
    private List<Long> roles;
    private Boolean system;
    private ZonedDateTime dateCreated;
    private ZonedDateTime dateUpdated;

    public Category() {
    }

    public Category(CategoryRequest request) {
        this.name = request.getName();
        this.description = request.getDescription();
        this.roles = request.getRoles();
        final ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
        this.dateCreated = now;
        this.dateUpdated = now;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Long> getRoles() {
        return roles;
    }

    public void setRoles(List<Long> roles) {
        this.roles = roles;
    }

    public Boolean getSystem() {
        return system;
    }

    public void setSystem(Boolean system) {
        this.system = system;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public ZonedDateTime getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
        this.dateUpdated = dateUpdated;
    }
}
