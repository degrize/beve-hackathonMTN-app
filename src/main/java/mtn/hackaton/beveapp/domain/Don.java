package mtn.hackaton.beveapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Don entity.\n@author BEVE.
 */
@Entity
@Table(name = "don")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "date_don")
    private LocalDate dateDon;

    @ManyToOne
    private Transaction transaction;

    @ManyToOne
    @JsonIgnoreProperties(value = { "inspirations", "categorieCreateurs", "reseauxSociauxes", "souscriptions" }, allowSetters = true)
    private CreateurAfricain createurAfricain;

    @ManyToOne
    private Donnateur donnateur;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Don id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Don description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDon() {
        return this.dateDon;
    }

    public Don dateDon(LocalDate dateDon) {
        this.setDateDon(dateDon);
        return this;
    }

    public void setDateDon(LocalDate dateDon) {
        this.dateDon = dateDon;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Don transaction(Transaction transaction) {
        this.setTransaction(transaction);
        return this;
    }

    public CreateurAfricain getCreateurAfricain() {
        return this.createurAfricain;
    }

    public void setCreateurAfricain(CreateurAfricain createurAfricain) {
        this.createurAfricain = createurAfricain;
    }

    public Don createurAfricain(CreateurAfricain createurAfricain) {
        this.setCreateurAfricain(createurAfricain);
        return this;
    }

    public Donnateur getDonnateur() {
        return this.donnateur;
    }

    public void setDonnateur(Donnateur donnateur) {
        this.donnateur = donnateur;
    }

    public Don donnateur(Donnateur donnateur) {
        this.setDonnateur(donnateur);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Don)) {
            return false;
        }
        return id != null && id.equals(((Don) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Don{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateDon='" + getDateDon() + "'" +
            "}";
    }
}
