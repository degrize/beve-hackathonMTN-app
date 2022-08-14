package mtn.hackaton.beveapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.Inspiration} entity.
 */
@Schema(description = "not an ignored comment")
public class InspirationDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String articleInspiration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getArticleInspiration() {
        return articleInspiration;
    }

    public void setArticleInspiration(String articleInspiration) {
        this.articleInspiration = articleInspiration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InspirationDTO)) {
            return false;
        }

        InspirationDTO inspirationDTO = (InspirationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, inspirationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InspirationDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", articleInspiration='" + getArticleInspiration() + "'" +
            "}";
    }
}
