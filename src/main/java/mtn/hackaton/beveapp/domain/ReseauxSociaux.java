package mtn.hackaton.beveapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ReseauxSociaux.
 */
@Entity
@Table(name = "reseaux_sociaux")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ReseauxSociaux implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "nom_chaine")
    private String nomChaine;

    @Column(name = "lien_chaine")
    private String lienChaine;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ReseauxSociaux id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ReseauxSociaux nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomChaine() {
        return this.nomChaine;
    }

    public ReseauxSociaux nomChaine(String nomChaine) {
        this.setNomChaine(nomChaine);
        return this;
    }

    public void setNomChaine(String nomChaine) {
        this.nomChaine = nomChaine;
    }

    public String getLienChaine() {
        return this.lienChaine;
    }

    public ReseauxSociaux lienChaine(String lienChaine) {
        this.setLienChaine(lienChaine);
        return this;
    }

    public void setLienChaine(String lienChaine) {
        this.lienChaine = lienChaine;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReseauxSociaux)) {
            return false;
        }
        return id != null && id.equals(((ReseauxSociaux) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReseauxSociaux{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", nomChaine='" + getNomChaine() + "'" +
            ", lienChaine='" + getLienChaine() + "'" +
            "}";
    }
}
