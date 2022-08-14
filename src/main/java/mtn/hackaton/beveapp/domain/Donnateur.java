package mtn.hackaton.beveapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Forfait;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Donnateur entity.\n@author BEVE.
 */
@Entity
@Table(name = "donnateur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Donnateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom_de_famille", nullable = false)
    private String nomDeFamille;

    @Column(name = "prenom")
    private String prenom;

    @NotNull
    @Column(name = "contact_1", nullable = false)
    private String contact1;

    @Column(name = "contact_2")
    private String contact2;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Sexe sexe;

    @Column(name = "date_de_naissance")
    private String dateDeNaissance;

    @Column(name = "pays")
    private String pays;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Enumerated(EnumType.STRING)
    @Column(name = "forfait")
    private Forfait forfait;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Donnateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomDeFamille() {
        return this.nomDeFamille;
    }

    public Donnateur nomDeFamille(String nomDeFamille) {
        this.setNomDeFamille(nomDeFamille);
        return this;
    }

    public void setNomDeFamille(String nomDeFamille) {
        this.nomDeFamille = nomDeFamille;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Donnateur prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getContact1() {
        return this.contact1;
    }

    public Donnateur contact1(String contact1) {
        this.setContact1(contact1);
        return this;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return this.contact2;
    }

    public Donnateur contact2(String contact2) {
        this.setContact2(contact2);
        return this;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getEmail() {
        return this.email;
    }

    public Donnateur email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexe getSexe() {
        return this.sexe;
    }

    public Donnateur sexe(Sexe sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Sexe sexe) {
        this.sexe = sexe;
    }

    public String getDateDeNaissance() {
        return this.dateDeNaissance;
    }

    public Donnateur dateDeNaissance(String dateDeNaissance) {
        this.setDateDeNaissance(dateDeNaissance);
        return this;
    }

    public void setDateDeNaissance(String dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getPays() {
        return this.pays;
    }

    public Donnateur pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Donnateur dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Forfait getForfait() {
        return this.forfait;
    }

    public Donnateur forfait(Forfait forfait) {
        this.setForfait(forfait);
        return this;
    }

    public void setForfait(Forfait forfait) {
        this.forfait = forfait;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Donnateur)) {
            return false;
        }
        return id != null && id.equals(((Donnateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Donnateur{" +
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
