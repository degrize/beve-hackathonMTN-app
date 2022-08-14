package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mtn.hackaton.beveapp.repository.InspirationRepository;
import mtn.hackaton.beveapp.service.InspirationService;
import mtn.hackaton.beveapp.service.dto.InspirationDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.Inspiration}.
 */
@RestController
@RequestMapping("/api")
public class InspirationResource {

    private final Logger log = LoggerFactory.getLogger(InspirationResource.class);

    private static final String ENTITY_NAME = "inspiration";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InspirationService inspirationService;

    private final InspirationRepository inspirationRepository;

    public InspirationResource(InspirationService inspirationService, InspirationRepository inspirationRepository) {
        this.inspirationService = inspirationService;
        this.inspirationRepository = inspirationRepository;
    }

    /**
     * {@code POST  /inspirations} : Create a new inspiration.
     *
     * @param inspirationDTO the inspirationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new inspirationDTO, or with status {@code 400 (Bad Request)} if the inspiration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/inspirations")
    public ResponseEntity<InspirationDTO> createInspiration(@Valid @RequestBody InspirationDTO inspirationDTO) throws URISyntaxException {
        log.debug("REST request to save Inspiration : {}", inspirationDTO);
        if (inspirationDTO.getId() != null) {
            throw new BadRequestAlertException("A new inspiration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspirationDTO result = inspirationService.save(inspirationDTO);
        return ResponseEntity
            .created(new URI("/api/inspirations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /inspirations/:id} : Updates an existing inspiration.
     *
     * @param id the id of the inspirationDTO to save.
     * @param inspirationDTO the inspirationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspirationDTO,
     * or with status {@code 400 (Bad Request)} if the inspirationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the inspirationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/inspirations/{id}")
    public ResponseEntity<InspirationDTO> updateInspiration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody InspirationDTO inspirationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Inspiration : {}, {}", id, inspirationDTO);
        if (inspirationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspirationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspirationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        InspirationDTO result = inspirationService.update(inspirationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspirationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /inspirations/:id} : Partial updates given fields of an existing inspiration, field will ignore if it is null
     *
     * @param id the id of the inspirationDTO to save.
     * @param inspirationDTO the inspirationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated inspirationDTO,
     * or with status {@code 400 (Bad Request)} if the inspirationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the inspirationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the inspirationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/inspirations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<InspirationDTO> partialUpdateInspiration(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody InspirationDTO inspirationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Inspiration partially : {}, {}", id, inspirationDTO);
        if (inspirationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, inspirationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!inspirationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<InspirationDTO> result = inspirationService.partialUpdate(inspirationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, inspirationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /inspirations} : get all the inspirations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of inspirations in body.
     */
    @GetMapping("/inspirations")
    public ResponseEntity<List<InspirationDTO>> getAllInspirations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Inspirations");
        Page<InspirationDTO> page = inspirationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /inspirations/:id} : get the "id" inspiration.
     *
     * @param id the id of the inspirationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the inspirationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/inspirations/{id}")
    public ResponseEntity<InspirationDTO> getInspiration(@PathVariable Long id) {
        log.debug("REST request to get Inspiration : {}", id);
        Optional<InspirationDTO> inspirationDTO = inspirationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(inspirationDTO);
    }

    /**
     * {@code DELETE  /inspirations/:id} : delete the "id" inspiration.
     *
     * @param id the id of the inspirationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/inspirations/{id}")
    public ResponseEntity<Void> deleteInspiration(@PathVariable Long id) {
        log.debug("REST request to delete Inspiration : {}", id);
        inspirationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
