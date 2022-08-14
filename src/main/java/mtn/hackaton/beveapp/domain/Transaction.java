package mtn.hackaton.beveapp.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Devise;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Transaction.
 */
@Entity
@Table(name = "transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "numero_mtn", nullable = false)
    private String numeroMtn;

    @NotNull
    @Column(name = "montant", nullable = false)
    private Double montant;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "devise", nullable = false)
    private Devise devise;

    @NotNull
    @Column(name = "date_transaction", nullable = false)
    private LocalDate dateTransaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Transaction id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMtn() {
        return this.numeroMtn;
    }

    public Transaction numeroMtn(String numeroMtn) {
        this.setNumeroMtn(numeroMtn);
        return this;
    }

    public void setNumeroMtn(String numeroMtn) {
        this.numeroMtn = numeroMtn;
    }

    public Double getMontant() {
        return this.montant;
    }

    public Transaction montant(Double montant) {
        this.setMontant(montant);
        return this;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Devise getDevise() {
        return this.devise;
    }

    public Transaction devise(Devise devise) {
        this.setDevise(devise);
        return this;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public LocalDate getDateTransaction() {
        return this.dateTransaction;
    }

    public Transaction dateTransaction(LocalDate dateTransaction) {
        this.setDateTransaction(dateTransaction);
        return this;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", numeroMtn='" + getNumeroMtn() + "'" +
            ", montant=" + getMontant() +
            ", devise='" + getDevise() + "'" +
            ", dateTransaction='" + getDateTransaction() + "'" +
            "}";
    }
}
