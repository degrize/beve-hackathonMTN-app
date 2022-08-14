package mtn.hackaton.beveapp.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * not an ignored comment
 */
@Entity
@Table(name = "inspiration")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inspiration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "article_inspiration")
    private String articleInspiration;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Inspiration id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Inspiration nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getArticleInspiration() {
        return this.articleInspiration;
    }

    public Inspiration articleInspiration(String articleInspiration) {
        this.setArticleInspiration(articleInspiration);
        return this;
    }

    public void setArticleInspiration(String articleInspiration) {
        this.articleInspiration = articleInspiration;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inspiration)) {
            return false;
        }
        return id != null && id.equals(((Inspiration) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inspiration{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", articleInspiration='" + getArticleInspiration() + "'" +
            "}";
    }
}
