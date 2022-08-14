package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mtn.hackaton.beveapp.repository.CategorieCreateurRepository;
import mtn.hackaton.beveapp.service.CategorieCreateurService;
import mtn.hackaton.beveapp.service.dto.CategorieCreateurDTO;
import mtn.hackaton.beveapp.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.CategorieCreateur}.
 */
@RestController
@RequestMapping("/api")
public class CategorieCreateurResource {

    private final Logger log = LoggerFactory.getLogger(CategorieCreateurResource.class);

    private static final String ENTITY_NAME = "categorieCreateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CategorieCreateurService categorieCreateurService;

    private final CategorieCreateurRepository categorieCreateurRepository;

    public CategorieCreateurResource(
        CategorieCreateurService categorieCreateurService,
        CategorieCreateurRepository categorieCreateurRepository
    ) {
        this.categorieCreateurService = categorieCreateurService;
        this.categorieCreateurRepository = categorieCreateurRepository;
    }

    /**
     * {@code POST  /categorie-createurs} : Create a new categorieCreateur.
     *
     * @param categorieCreateurDTO the categorieCreateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new categorieCreateurDTO, or with status {@code 400 (Bad Request)} if the categorieCreateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/categorie-createurs")
    public ResponseEntity<CategorieCreateurDTO> createCategorieCreateur(@RequestBody CategorieCreateurDTO categorieCreateurDTO)
        throws URISyntaxException {
        log.debug("REST request to save CategorieCreateur : {}", categorieCreateurDTO);
        if (categorieCreateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new categorieCreateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CategorieCreateurDTO result = categorieCreateurService.save(categorieCreateurDTO);
        return ResponseEntity
            .created(new URI("/api/categorie-createurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /categorie-createurs/:id} : Updates an existing categorieCreateur.
     *
     * @param id the id of the categorieCreateurDTO to save.
     * @param categorieCreateurDTO the categorieCreateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieCreateurDTO,
     * or with status {@code 400 (Bad Request)} if the categorieCreateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the categorieCreateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/categorie-createurs/{id}")
    public ResponseEntity<CategorieCreateurDTO> updateCategorieCreateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieCreateurDTO categorieCreateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CategorieCreateur : {}, {}", id, categorieCreateurDTO);
        if (categorieCreateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieCreateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieCreateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CategorieCreateurDTO result = categorieCreateurService.update(categorieCreateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieCreateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /categorie-createurs/:id} : Partial updates given fields of an existing categorieCreateur, field will ignore if it is null
     *
     * @param id the id of the categorieCreateurDTO to save.
     * @param categorieCreateurDTO the categorieCreateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated categorieCreateurDTO,
     * or with status {@code 400 (Bad Request)} if the categorieCreateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the categorieCreateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the categorieCreateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/categorie-createurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CategorieCreateurDTO> partialUpdateCategorieCreateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CategorieCreateurDTO categorieCreateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CategorieCreateur partially : {}, {}", id, categorieCreateurDTO);
        if (categorieCreateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, categorieCreateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!categorieCreateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CategorieCreateurDTO> result = categorieCreateurService.partialUpdate(categorieCreateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, categorieCreateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /categorie-createurs} : get all the categorieCreateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of categorieCreateurs in body.
     */
    @GetMapping("/categorie-createurs")
    public ResponseEntity<List<CategorieCreateurDTO>> getAllCategorieCreateurs(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CategorieCreateurs");
        Page<CategorieCreateurDTO> page = categorieCreateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /categorie-createurs/:id} : get the "id" categorieCreateur.
     *
     * @param id the id of the categorieCreateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the categorieCreateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/categorie-createurs/{id}")
    public ResponseEntity<CategorieCreateurDTO> getCategorieCreateur(@PathVariable Long id) {
        log.debug("REST request to get CategorieCreateur : {}", id);
        Optional<CategorieCreateurDTO> categorieCreateurDTO = categorieCreateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(categorieCreateurDTO);
    }

    /**
     * {@code DELETE  /categorie-createurs/:id} : delete the "id" categorieCreateur.
     *
     * @param id the id of the categorieCreateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/categorie-createurs/{id}")
    public ResponseEntity<Void> deleteCategorieCreateur(@PathVariable Long id) {
        log.debug("REST request to delete CategorieCreateur : {}", id);
        categorieCreateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
