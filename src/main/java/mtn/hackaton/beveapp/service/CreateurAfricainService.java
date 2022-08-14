package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.CreateurAfricain}.
 */
public interface CreateurAfricainService {
    /**
     * Save a createurAfricain.
     *
     * @param createurAfricainDTO the entity to save.
     * @return the persisted entity.
     */
    CreateurAfricainDTO save(CreateurAfricainDTO createurAfricainDTO);

    /**
     * Updates a createurAfricain.
     *
     * @param createurAfricainDTO the entity to update.
     * @return the persisted entity.
     */
    CreateurAfricainDTO update(CreateurAfricainDTO createurAfricainDTO);

    /**
     * Partially updates a createurAfricain.
     *
     * @param createurAfricainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CreateurAfricainDTO> partialUpdate(CreateurAfricainDTO createurAfricainDTO);

    /**
     * Get all the createurAfricains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CreateurAfricainDTO> findAll(Pageable pageable);

    /**
     * Get the "id" createurAfricain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CreateurAfricainDTO> findOne(Long id);

    /**
     * Delete the "id" createurAfricain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
