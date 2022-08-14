package mtn.hackaton.beveapp.repository;

import java.util.List;
import java.util.Optional;
import mtn.hackaton.beveapp.domain.Don;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Don entity.
 */
@Repository
public interface DonRepository extends JpaRepository<Don, Long> {
    default Optional<Don> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Don> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Don> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct don from Don don left join fetch don.transaction left join fetch don.createurAfricain left join fetch don.donnateur",
        countQuery = "select count(distinct don) from Don don"
    )
    Page<Don> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select distinct don from Don don left join fetch don.transaction left join fetch don.createurAfricain left join fetch don.donnateur"
    )
    List<Don> findAllWithToOneRelationships();

    @Query(
        "select don from Don don left join fetch don.transaction left join fetch don.createurAfricain left join fetch don.donnateur where don.id =:id"
    )
    Optional<Don> findOneWithToOneRelationships(@Param("id") Long id);
}
