package mtn.hackaton.beveapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.ReseauxSociaux} entity.
 */
public class ReseauxSociauxDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private String nomChaine;

    private String lienChaine;

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

    public String getNomChaine() {
        return nomChaine;
    }

    public void setNomChaine(String nomChaine) {
        this.nomChaine = nomChaine;
    }

    public String getLienChaine() {
        return lienChaine;
    }

    public void setLienChaine(String lienChaine) {
        this.lienChaine = lienChaine;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReseauxSociauxDTO)) {
            return false;
        }

        ReseauxSociauxDTO reseauxSociauxDTO = (ReseauxSociauxDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, reseauxSociauxDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReseauxSociauxDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomChaine='" + getNomChaine() + "'" +
            ", lienChaine='" + getLienChaine() + "'" +
            "}";
    }
}
