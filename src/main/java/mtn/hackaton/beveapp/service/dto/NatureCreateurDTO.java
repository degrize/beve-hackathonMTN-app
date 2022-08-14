package mtn.hackaton.beveapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.NatureCreateur} entity.
 */
public class NatureCreateurDTO implements Serializable {

    private Long id;

    private String type;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NatureCreateurDTO)) {
            return false;
        }

        NatureCreateurDTO natureCreateurDTO = (NatureCreateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, natureCreateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NatureCreateurDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
