package mtn.hackaton.beveapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;
import mtn.hackaton.beveapp.domain.enumeration.Devise;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String numeromtn;

    @NotNull
    private Double montant;

    @NotNull
    private Devise devise;

    @NotNull
    private LocalDate dateTransaction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeromtn() {
        return numeromtn;
    }

    public void setNumeromtn(String numeromtn) {
        this.numeromtn = numeromtn;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Devise getDevise() {
        return devise;
    }

    public void setDevise(Devise devise) {
        this.devise = devise;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionDTO)) {
            return false;
        }

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transactionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", numeromtn='" + getNumeromtn() + "'" +
            ", montant=" + getMontant() +
            ", devise='" + getDevise() + "'" +
            ", dateTransaction='" + getDateTransaction() + "'" +
            "}";
    }
}
