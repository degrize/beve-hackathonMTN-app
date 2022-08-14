package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.SouscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.Souscription}.
 */
public interface SouscriptionService {
    /**
     * Save a souscription.
     *
     * @param souscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    SouscriptionDTO save(SouscriptionDTO souscriptionDTO);

    /**
     * Updates a souscription.
     *
     * @param souscriptionDTO the entity to update.
     * @return the persisted entity.
     */
    SouscriptionDTO update(SouscriptionDTO souscriptionDTO);

    /**
     * Partially updates a souscription.
     *
     * @param souscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SouscriptionDTO> partialUpdate(SouscriptionDTO souscriptionDTO);

    /**
     * Get all the souscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SouscriptionDTO> findAll(Pageable pageable);

    /**
     * Get all the souscriptions with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SouscriptionDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" souscription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SouscriptionDTO> findOne(Long id);

    /**
     * Delete the "id" souscription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
