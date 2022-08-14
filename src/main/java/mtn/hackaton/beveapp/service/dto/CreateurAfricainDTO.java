package mtn.hackaton.beveapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;
import mtn.hackaton.beveapp.domain.enumeration.SituationMatrimoniale;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.CreateurAfricain} entity.
 */
@Schema(description = "CreateurAfricain entity.\n@author BEVE.")
public class CreateurAfricainDTO implements Serializable {

    private Long id;

    @NotNull
    private String nomDeFamille;

    @NotNull
    private String prenom;

    @NotNull
    private String label;

    private String surnom;

    @NotNull
    private String contact1;

    private String contact2;

    @NotNull
    private Sexe sexe;

    @NotNull
    private String email;

    private String dateDeNaissance;

    @NotNull
    private String pays;

    private String ville;

    private String adresse;

    private SituationMatrimoniale situationMatrimoniale;

    private LocalDate dateDebut;

    private Set<InspirationDTO> inspirations = new HashSet<>();

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
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

    public Sexe getSexe() {
        return sexe;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return situationMatrimoniale;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Set<InspirationDTO> getInspirations() {
        return inspirations;
    }

    public void setInspirations(Set<InspirationDTO> inspirations) {
        this.inspirations = inspirations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateurAfricainDTO)) {
            return false;
        }

        CreateurAfricainDTO createurAfricainDTO = (CreateurAfricainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, createurAfricainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreateurAfricainDTO{" +
            "id=" + getId() +
            ", nomDeFamille='" + getNomDeFamille() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", label='" + getLabel() + "'" +
            ", surnom='" + getSurnom() + "'" +
            ", contact1='" + getContact1() + "'" +
            ", contact2='" + getContact2() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", email='" + getEmail() + "'" +
            ", dateDeNaissance='" + getDateDeNaissance() + "'" +
            ", pays='" + getPays() + "'" +
            ", ville='" + getVille() + "'" +
            ", adresse='" + getAdresse() + "'" +
            ", situationMatrimoniale='" + getSituationMatrimoniale() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", inspirations=" + getInspirations() +
            "}";
    }
}
