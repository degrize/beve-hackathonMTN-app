package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.Donnateur}.
 */
public interface DonnateurService {
    /**
     * Save a donnateur.
     *
     * @param donnateurDTO the entity to save.
     * @return the persisted entity.
     */
    DonnateurDTO save(DonnateurDTO donnateurDTO);

    /**
     * Updates a donnateur.
     *
     * @param donnateurDTO the entity to update.
     * @return the persisted entity.
     */
    DonnateurDTO update(DonnateurDTO donnateurDTO);

    /**
     * Partially updates a donnateur.
     *
     * @param donnateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DonnateurDTO> partialUpdate(DonnateurDTO donnateurDTO);

    /**
     * Get all the donnateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DonnateurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" donnateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DonnateurDTO> findOne(Long id);

    /**
     * Delete the "id" donnateur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
