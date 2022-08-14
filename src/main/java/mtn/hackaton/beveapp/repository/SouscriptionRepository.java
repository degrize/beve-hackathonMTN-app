package mtn.hackaton.beveapp.repository;

import mtn.hackaton.beveapp.domain.Souscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Souscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SouscriptionRepository extends JpaRepository<Souscription, Long> {}
