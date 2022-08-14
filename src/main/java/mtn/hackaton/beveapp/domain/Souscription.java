package mtn.hackaton.beveapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.EtatCompte;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Souscription.
 */
@Entity
@Table(name = "souscription")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Souscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatCompte etat;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Column(name = "pourcentage_du_don", nullable = false)
    private Double pourcentageDuDon;

    @ManyToMany
    @JoinTable(
        name = "rel_souscription__createur_africain",
        joinColumns = @JoinColumn(name = "souscription_id"),
        inverseJoinColumns = @JoinColumn(name = "createur_africain_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspirations", "categorieCreateurs", "reseauxSociauxes", "souscriptions" }, allowSetters = true)
    private Set<CreateurAfricain> createurAfricains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Souscription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EtatCompte getEtat() {
        return this.etat;
    }

    public Souscription etat(EtatCompte etat) {
        this.setEtat(etat);
        return this;
    }

    public void setEtat(EtatCompte etat) {
        this.etat = etat;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Souscription montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getPourcentageDuDon() {
        return this.pourcentageDuDon;
    }

    public Souscription pourcentageDuDon(Double pourcentageDuDon) {
        this.setPourcentageDuDon(pourcentageDuDon);
        return this;
    }

    public void setPourcentageDuDon(Double pourcentageDuDon) {
        this.pourcentageDuDon = pourcentageDuDon;
    }

    public Set<CreateurAfricain> getCreateurAfricains() {
        return this.createurAfricains;
    }

    public void setCreateurAfricains(Set<CreateurAfricain> createurAfricains) {
        this.createurAfricains = createurAfricains;
    }

    public Souscription createurAfricains(Set<CreateurAfricain> createurAfricains) {
        this.setCreateurAfricains(createurAfricains);
        return this;
    }

    public Souscription addCreateurAfricain(CreateurAfricain createurAfricain) {
        this.createurAfricains.add(createurAfricain);
        createurAfricain.getSouscriptions().add(this);
        return this;
    }

    public Souscription removeCreateurAfricain(CreateurAfricain createurAfricain) {
        this.createurAfricains.remove(createurAfricain);
        createurAfricain.getSouscriptions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Souscription)) {
            return false;
        }
        return id != null && id.equals(((Souscription) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Souscription{" +
            "id=" + getId() +
            ", etat='" + getEtat() + "'" +
            ", montant=" + getMontant() +
            ", pourcentageDuDon=" + getPourcentageDuDon() +
            "}";
    }
}
