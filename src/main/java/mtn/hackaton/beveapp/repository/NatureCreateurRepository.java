package mtn.hackaton.beveapp.repository;

import mtn.hackaton.beveapp.domain.NatureCreateur;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NatureCreateur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NatureCreateurRepository extends JpaRepository<NatureCreateur, Long> {}
