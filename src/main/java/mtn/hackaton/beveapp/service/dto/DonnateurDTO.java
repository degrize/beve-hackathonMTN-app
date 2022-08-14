package mtn.hackaton.beveapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Forfait;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.Donnateur} entity.
 */
@Schema(description = "Donnateur entity.\n@author BEVE.")
public class DonnateurDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomDeFamille;

    private String prenom;

    @NotNull
    private String contact1;

    private String contact2;

    private String email;

    private Sexe sexe;

    private String dateDeNaissance;

    private String pays;

    private LocalDate dateDebut;

    private Forfait forfait;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeFamille() {
        return nomDeFamille;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Forfait getForfait() {
        return forfait;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonnateurDTO)) {
            return false;
        }

        DonnateurDTO donnateurDTO = (DonnateurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donnateurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonnateurDTO{" +
            "id=" + getId() +
            ", nomDeFamille='" + getNomDeFamille() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", contact1='" + getContact1() + "'" +
            ", contact2='" + getContact2() + "'" +
            ", email='" + getEmail() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", pays='" + getPays() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", forfait='" + getForfait() + "'" +
            "}";
    }
}
