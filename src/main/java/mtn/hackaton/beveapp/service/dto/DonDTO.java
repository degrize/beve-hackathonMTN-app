package mtn.hackaton.beveapp.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link mtn.hackaton.beveapp.domain.Don} entity.
 */
@Schema(description = "Don entity.\n@author BEVE.")
public class DonDTO implements Serializable {

    private Long id;

    private String description;

    private LocalDate dateDon;

    private TransactionDTO transaction;

    private CreateurAfricainDTO createurAfricain;

    private DonnateurDTO donnateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateDon() {
        return dateDon;
    }

    public void setDateDon(LocalDate dateDon) {
        this.dateDon = dateDon;
    }

    public TransactionDTO getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionDTO transaction) {
        this.transaction = transaction;
    }

    public CreateurAfricainDTO getCreateurAfricain() {
        return createurAfricain;
    }

    public void setCreateurAfricain(CreateurAfricainDTO createurAfricain) {
        this.createurAfricain = createurAfricain;
    }

    public DonnateurDTO getDonnateur() {
        return donnateur;
    }

    public void setDonnateur(DonnateurDTO donnateur) {
        this.donnateur = donnateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DonDTO)) {
            return false;
        }

        DonDTO donDTO = (DonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, donDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DonDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", dateDon='" + getDateDon() + "'" +
            ", transaction=" + getTransaction() +
            ", createurAfricain=" + getCreateurAfricain() +
            ", donnateur=" + getDonnateur() +
            "}";
    }
}
