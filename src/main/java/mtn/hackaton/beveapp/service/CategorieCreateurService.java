package mtn.hackaton.beveapp.service;

import java.util.Optional;
import mtn.hackaton.beveapp.service.dto.CategorieCreateurDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link mtn.hackaton.beveapp.domain.CategorieCreateur}.
 */
public interface CategorieCreateurService {
    /**
     * Save a categorieCreateur.
     *
     * @param categorieCreateurDTO the entity to save.
     * @return the persisted entity.
     */
    CategorieCreateurDTO save(CategorieCreateurDTO categorieCreateurDTO);

    /**
     * Updates a categorieCreateur.
     *
     * @param categorieCreateurDTO the entity to update.
     * @return the persisted entity.
     */
    CategorieCreateurDTO update(CategorieCreateurDTO categorieCreateurDTO);

    /**
     * Partially updates a categorieCreateur.
     *
     * @param categorieCreateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CategorieCreateurDTO> partialUpdate(CategorieCreateurDTO categorieCreateurDTO);

    /**
     * Get all the categorieCreateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CategorieCreateurDTO> findAll(Pageable pageable);

    /**
     * Get the "id" categorieCreateur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CategorieCreateurDTO> findOne(Long id);

    /**
     * Delete the "id" categorieCreateur.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
