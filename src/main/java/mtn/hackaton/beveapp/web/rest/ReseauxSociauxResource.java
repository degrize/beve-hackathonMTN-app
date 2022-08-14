package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mtn.hackaton.beveapp.repository.ReseauxSociauxRepository;
import mtn.hackaton.beveapp.service.ReseauxSociauxService;
import mtn.hackaton.beveapp.service.dto.ReseauxSociauxDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.ReseauxSociaux}.
 */
@RestController
@RequestMapping("/api")
public class ReseauxSociauxResource {

    private final Logger log = LoggerFactory.getLogger(ReseauxSociauxResource.class);

    private static final String ENTITY_NAME = "reseauxSociaux";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ReseauxSociauxService reseauxSociauxService;

    private final ReseauxSociauxRepository reseauxSociauxRepository;

    public ReseauxSociauxResource(ReseauxSociauxService reseauxSociauxService, ReseauxSociauxRepository reseauxSociauxRepository) {
        this.reseauxSociauxService = reseauxSociauxService;
        this.reseauxSociauxRepository = reseauxSociauxRepository;
    }

    /**
     * {@code POST  /reseaux-sociauxes} : Create a new reseauxSociaux.
     *
     * @param reseauxSociauxDTO the reseauxSociauxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new reseauxSociauxDTO, or with status {@code 400 (Bad Request)} if the reseauxSociaux has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/reseaux-sociauxes")
    public ResponseEntity<ReseauxSociauxDTO> createReseauxSociaux(@Valid @RequestBody ReseauxSociauxDTO reseauxSociauxDTO)
        throws URISyntaxException {
        log.debug("REST request to save ReseauxSociaux : {}", reseauxSociauxDTO);
        if (reseauxSociauxDTO.getId() != null) {
            throw new BadRequestAlertException("A new reseauxSociaux cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ReseauxSociauxDTO result = reseauxSociauxService.save(reseauxSociauxDTO);
        return ResponseEntity
            .created(new URI("/api/reseaux-sociauxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /reseaux-sociauxes/:id} : Updates an existing reseauxSociaux.
     *
     * @param id the id of the reseauxSociauxDTO to save.
     * @param reseauxSociauxDTO the reseauxSociauxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reseauxSociauxDTO,
     * or with status {@code 400 (Bad Request)} if the reseauxSociauxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the reseauxSociauxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/reseaux-sociauxes/{id}")
    public ResponseEntity<ReseauxSociauxDTO> updateReseauxSociaux(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ReseauxSociauxDTO reseauxSociauxDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ReseauxSociaux : {}, {}", id, reseauxSociauxDTO);
        if (reseauxSociauxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reseauxSociauxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reseauxSociauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ReseauxSociauxDTO result = reseauxSociauxService.update(reseauxSociauxDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reseauxSociauxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /reseaux-sociauxes/:id} : Partial updates given fields of an existing reseauxSociaux, field will ignore if it is null
     *
     * @param id the id of the reseauxSociauxDTO to save.
     * @param reseauxSociauxDTO the reseauxSociauxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated reseauxSociauxDTO,
     * or with status {@code 400 (Bad Request)} if the reseauxSociauxDTO is not valid,
     * or with status {@code 404 (Not Found)} if the reseauxSociauxDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the reseauxSociauxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/reseaux-sociauxes/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ReseauxSociauxDTO> partialUpdateReseauxSociaux(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ReseauxSociauxDTO reseauxSociauxDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ReseauxSociaux partially : {}, {}", id, reseauxSociauxDTO);
        if (reseauxSociauxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, reseauxSociauxDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!reseauxSociauxRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ReseauxSociauxDTO> result = reseauxSociauxService.partialUpdate(reseauxSociauxDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, reseauxSociauxDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /reseaux-sociauxes} : get all the reseauxSociauxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of reseauxSociauxes in body.
     */
    @GetMapping("/reseaux-sociauxes")
    public ResponseEntity<List<ReseauxSociauxDTO>> getAllReseauxSociauxes(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of ReseauxSociauxes");
        Page<ReseauxSociauxDTO> page = reseauxSociauxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /reseaux-sociauxes/:id} : get the "id" reseauxSociaux.
     *
     * @param id the id of the reseauxSociauxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the reseauxSociauxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/reseaux-sociauxes/{id}")
    public ResponseEntity<ReseauxSociauxDTO> getReseauxSociaux(@PathVariable Long id) {
        log.debug("REST request to get ReseauxSociaux : {}", id);
        Optional<ReseauxSociauxDTO> reseauxSociauxDTO = reseauxSociauxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(reseauxSociauxDTO);
    }

    /**
     * {@code DELETE  /reseaux-sociauxes/:id} : delete the "id" reseauxSociaux.
     *
     * @param id the id of the reseauxSociauxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/reseaux-sociauxes/{id}")
    public ResponseEntity<Void> deleteReseauxSociaux(@PathVariable Long id) {
        log.debug("REST request to delete ReseauxSociaux : {}", id);
        reseauxSociauxService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
