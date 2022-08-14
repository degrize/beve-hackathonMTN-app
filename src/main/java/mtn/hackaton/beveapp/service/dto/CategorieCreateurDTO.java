package mtn.hackaton.beveapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.CategorieCreateur} entity.
 */
public class CategorieCreateurDTO implements Serializable {

    private Long id;

    private String categorie;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
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
        if (!(o instanceof CategorieCreateurDTO)) {
            return false;
        }

        CategorieCreateurDTO categorieCreateurDTO = (CategorieCreateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categorieCreateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCreateurDTO{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
