package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.ReseauxSociauxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.ReseauxSociaux}.
 */
public interface ReseauxSociauxService {
    /**
     * Save a reseauxSociaux.
     *
     * @param reseauxSociauxDTO the entity to save.
     * @return the persisted entity.
     */
    ReseauxSociauxDTO save(ReseauxSociauxDTO reseauxSociauxDTO);

    /**
     * Updates a reseauxSociaux.
     *
     * @param reseauxSociauxDTO the entity to update.
     * @return the persisted entity.
     */
    ReseauxSociauxDTO update(ReseauxSociauxDTO reseauxSociauxDTO);

    /**
     * Partially updates a reseauxSociaux.
     *
     * @param reseauxSociauxDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ReseauxSociauxDTO> partialUpdate(ReseauxSociauxDTO reseauxSociauxDTO);

    /**
     * Get all the reseauxSociauxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ReseauxSociauxDTO> findAll(Pageable pageable);

    /**
     * Get the "id" reseauxSociaux.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ReseauxSociauxDTO> findOne(Long id);

    /**
     * Delete the "id" reseauxSociaux.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
