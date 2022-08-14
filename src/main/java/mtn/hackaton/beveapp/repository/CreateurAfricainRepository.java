package mtn.hackaton.beveapp.repository;

import mtn.hackaton.beveapp.domain.CreateurAfricain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CreateurAfricain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CreateurAfricainRepository extends JpaRepository<CreateurAfricain, Long> {}
