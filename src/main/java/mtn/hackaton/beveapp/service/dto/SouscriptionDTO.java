package mtn.hackaton.beveapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.EtatCompte;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.Souscription} entity.
 */
public class SouscriptionDTO implements Serializable {

    private Long id;

    @NotNull
    private EtatCompte etat;

    @NotNull
    private Double montant;

    @NotNull
    private Double pourcentageDuDon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatCompte getEtat() {
        return etat;
    }

    public void setEtat(EtatCompte etat) {
        this.etat = etat;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getPourcentageDuDon() {
        return pourcentageDuDon;
    }

    public void setPourcentageDuDon(Double pourcentageDuDon) {
        this.pourcentageDuDon = pourcentageDuDon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SouscriptionDTO)) {
            return false;
        }

        SouscriptionDTO souscriptionDTO = (SouscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, souscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SouscriptionDTO{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", montant=" + getMontant() +
            ", pourcentageDuDon=" + getPourcentageDuDon() +
            "}";
    }
}
