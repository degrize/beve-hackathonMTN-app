package mtn.hackaton.beveapp.repository;

import mtn.hackaton.beveapp.domain.CategorieCreateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CategorieCreateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CategorieCreateurRepository extends JpaRepository<CategorieCreateur, Long> {}
