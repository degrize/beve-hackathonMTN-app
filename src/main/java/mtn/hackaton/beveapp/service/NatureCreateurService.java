package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.NatureCreateurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.NatureCreateur}.
 */
public interface NatureCreateurService {
    /**
     * Save a natureCreateur.
     *
     * @param natureCreateurDTO the entity to save.
     * @return the persisted entity.
     */
    NatureCreateurDTO save(NatureCreateurDTO natureCreateurDTO);

    /**
     * Updates a natureCreateur.
     *
     * @param natureCreateurDTO the entity to update.
     * @return the persisted entity.
     */
    NatureCreateurDTO update(NatureCreateurDTO natureCreateurDTO);

    /**
     * Partially updates a natureCreateur.
     *
     * @param natureCreateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<NatureCreateurDTO> partialUpdate(NatureCreateurDTO natureCreateurDTO);

    /**
     * Get all the natureCreateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<NatureCreateurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" natureCreateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<NatureCreateurDTO> findOne(Long id);

    /**
     * Delete the "id" natureCreateur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
