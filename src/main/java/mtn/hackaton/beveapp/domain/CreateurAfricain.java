package mtn.hackaton.beveapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;
import mtn.hackaton.beveapp.domain.enumeration.SituationMatrimoniale;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * CreateurAfricain entity.\n@author BEVE.
 */
@Entity
@Table(name = "createur_africain")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CreateurAfricain implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_de_famille", nullable = false)
    private String nomDeFamille;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "surnom")
    private String surnom;

    @NotNull
    @Column(name = "contact_1", nullable = false)
    private String contact1;

    @Column(name = "contact_2")
    private String contact2;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "date_de_naissance")
    private String dateDeNaissance;

    @NotNull
    @Column(name = "pays", nullable = false)
    private String pays;

    @Column(name = "ville")
    private String ville;

    @Column(name = "adresse")
    private String adresse;

    @Enumerated(EnumType.STRING)
    @Column(name = "situation_matrimoniale")
    private SituationMatrimoniale situationMatrimoniale;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CreateurAfricain id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeFamille() {
        return this.nomDeFamille;
    }

    public CreateurAfricain nomDeFamille(String nomDeFamille) {
        this.setNomDeFamille(nomDeFamille);
        return this;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public CreateurAfricain prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLabel() {
        return this.label;
    }

    public CreateurAfricain label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSurnom() {
        return this.surnom;
    }

    public CreateurAfricain surnom(String surnom) {
        this.setSurnom(surnom);
        return this;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public String getContact1() {
        return this.contact1;
    }

    public CreateurAfricain contact1(String contact1) {
        this.setContact1(contact1);
        return this;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return this.contact2;
    }

    public CreateurAfricain contact2(String contact2) {
        this.setContact2(contact2);
        return this;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public CreateurAfricain sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return this.email;
    }

    public CreateurAfricain email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public CreateurAfricain dateDeNaissance(String dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getPays() {
        return this.pays;
    }

    public CreateurAfricain pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getVille() {
        return this.ville;
    }

    public CreateurAfricain ville(String ville) {
        this.setVille(ville);
        return this;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public CreateurAfricain adresse(String adresse) {
        this.setAdresse(adresse);
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public SituationMatrimoniale getSituationMatrimoniale() {
        return this.situationMatrimoniale;
    }

    public CreateurAfricain situationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.setSituationMatrimoniale(situationMatrimoniale);
        return this;
    }

    public void setSituationMatrimoniale(SituationMatrimoniale situationMatrimoniale) {
        this.situationMatrimoniale = situationMatrimoniale;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public CreateurAfricain dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CreateurAfricain)) {
            return false;
        }
        return id != null && id.equals(((CreateurAfricain) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CreateurAfricain{" +
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
            "}";
    }
}
