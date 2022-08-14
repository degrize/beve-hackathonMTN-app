package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.DonDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.Don}.
 */
public interface DonService {
    /**
     * Save a don.
     *
     * @param donDTO the entity to save.
     * @return the persisted entity.
     */
    DonDTO save(DonDTO donDTO);

    /**
     * Updates a don.
     *
     * @param donDTO the entity to update.
     * @return the persisted entity.
     */
    DonDTO update(DonDTO donDTO);

    /**
     * Partially updates a don.
     *
     * @param donDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonDTO> partialUpdate(DonDTO donDTO);

    /**
     * Get all the dons.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonDTO> findAll(Pageable pageable);

    /**
     * Get all the dons with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" don.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonDTO> findOne(Long id);

    /**
     * Delete the "id" don.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
