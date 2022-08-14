package mtn.hackaton.beveapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CategorieCreateur.
 */
@Entity
@Table(name = "categorie_createur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CategorieCreateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "categorie")
    private String categorie;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "categorieCreateurs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "inspirations", "categorieCreateurs", "reseauxSociauxes", "souscriptions" }, allowSetters = true)
    private Set<CreateurAfricain> createurAfricains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CategorieCreateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategorie() {
        return this.categorie;
    }

    public CategorieCreateur categorie(String categorie) {
        this.setCategorie(categorie);
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return this.description;
    }

    public CategorieCreateur description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CreateurAfricain> getCreateurAfricains() {
        return this.createurAfricains;
    }

    public void setCreateurAfricains(Set<CreateurAfricain> createurAfricains) {
        if (this.createurAfricains != null) {
            this.createurAfricains.forEach(i -> i.removeCategorieCreateur(this));
        }
        if (createurAfricains != null) {
            createurAfricains.forEach(i -> i.addCategorieCreateur(this));
        }
        this.createurAfricains = createurAfricains;
    }

    public CategorieCreateur createurAfricains(Set<CreateurAfricain> createurAfricains) {
        this.setCreateurAfricains(createurAfricains);
        return this;
    }

    public CategorieCreateur addCreateurAfricain(CreateurAfricain createurAfricain) {
        this.createurAfricains.add(createurAfricain);
        createurAfricain.getCategorieCreateurs().add(this);
        return this;
    }

    public CategorieCreateur removeCreateurAfricain(CreateurAfricain createurAfricain) {
        this.createurAfricains.remove(createurAfricain);
        createurAfricain.getCategorieCreateurs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategorieCreateur)) {
            return false;
        }
        return id != null && id.equals(((CategorieCreateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CategorieCreateur{" +
            "id=" + getId() +
            ", categorie='" + getCategorie() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
