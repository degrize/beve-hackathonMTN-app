package mtn.hackaton.beveapp.repository;

import java.util.List;
import java.util.Optional;
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreateurAfricain entity.
 *
 * When extending this class, extend CreateurAfricainRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface CreateurAfricainRepository extends CreateurAfricainRepositoryWithBagRelationships, JpaRepository<CreateurAfricain, Long> {
    default Optional<CreateurAfricain> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<CreateurAfricain> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<CreateurAfricain> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
