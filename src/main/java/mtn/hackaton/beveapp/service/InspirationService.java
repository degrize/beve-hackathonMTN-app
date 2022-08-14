package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.InspirationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.Inspiration}.
 */
public interface InspirationService {
    /**
     * Save a inspiration.
     *
     * @param inspirationDTO the entity to save.
     * @return the persisted entity.
     */
    InspirationDTO save(InspirationDTO inspirationDTO);

    /**
     * Updates a inspiration.
     *
     * @param inspirationDTO the entity to update.
     * @return the persisted entity.
     */
    InspirationDTO update(InspirationDTO inspirationDTO);

    /**
     * Partially updates a inspiration.
     *
     * @param inspirationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<InspirationDTO> partialUpdate(InspirationDTO inspirationDTO);

    /**
     * Get all the inspirations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InspirationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" inspiration.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InspirationDTO> findOne(Long id);

    /**
     * Delete the "id" inspiration.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
