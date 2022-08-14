package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mtn.hackaton.beveapp.repository.NatureCreateurRepository;
import mtn.hackaton.beveapp.service.NatureCreateurService;
import mtn.hackaton.beveapp.service.dto.NatureCreateurDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.NatureCreateur}.
 */
@RestController
@RequestMapping("/api")
public class NatureCreateurResource {

    private final Logger log = LoggerFactory.getLogger(NatureCreateurResource.class);

    private static final String ENTITY_NAME = "natureCreateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NatureCreateurService natureCreateurService;

    private final NatureCreateurRepository natureCreateurRepository;

    public NatureCreateurResource(NatureCreateurService natureCreateurService, NatureCreateurRepository natureCreateurRepository) {
        this.natureCreateurService = natureCreateurService;
        this.natureCreateurRepository = natureCreateurRepository;
    }

    /**
     * {@code POST  /nature-createurs} : Create a new natureCreateur.
     *
     * @param natureCreateurDTO the natureCreateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new natureCreateurDTO, or with status {@code 400 (Bad Request)} if the natureCreateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nature-createurs")
    public ResponseEntity<NatureCreateurDTO> createNatureCreateur(@RequestBody NatureCreateurDTO natureCreateurDTO)
        throws URISyntaxException {
        log.debug("REST request to save NatureCreateur : {}", natureCreateurDTO);
        if (natureCreateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new natureCreateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NatureCreateurDTO result = natureCreateurService.save(natureCreateurDTO);
        return ResponseEntity
            .created(new URI("/api/nature-createurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nature-createurs/:id} : Updates an existing natureCreateur.
     *
     * @param id the id of the natureCreateurDTO to save.
     * @param natureCreateurDTO the natureCreateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureCreateurDTO,
     * or with status {@code 400 (Bad Request)} if the natureCreateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the natureCreateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nature-createurs/{id}")
    public ResponseEntity<NatureCreateurDTO> updateNatureCreateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureCreateurDTO natureCreateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update NatureCreateur : {}, {}", id, natureCreateurDTO);
        if (natureCreateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureCreateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureCreateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        NatureCreateurDTO result = natureCreateurService.update(natureCreateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureCreateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /nature-createurs/:id} : Partial updates given fields of an existing natureCreateur, field will ignore if it is null
     *
     * @param id the id of the natureCreateurDTO to save.
     * @param natureCreateurDTO the natureCreateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated natureCreateurDTO,
     * or with status {@code 400 (Bad Request)} if the natureCreateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the natureCreateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the natureCreateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/nature-createurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<NatureCreateurDTO> partialUpdateNatureCreateur(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody NatureCreateurDTO natureCreateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update NatureCreateur partially : {}, {}", id, natureCreateurDTO);
        if (natureCreateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, natureCreateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!natureCreateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<NatureCreateurDTO> result = natureCreateurService.partialUpdate(natureCreateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, natureCreateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /nature-createurs} : get all the natureCreateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of natureCreateurs in body.
     */
    @GetMapping("/nature-createurs")
    public ResponseEntity<List<NatureCreateurDTO>> getAllNatureCreateurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of NatureCreateurs");
        Page<NatureCreateurDTO> page = natureCreateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /nature-createurs/:id} : get the "id" natureCreateur.
     *
     * @param id the id of the natureCreateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the natureCreateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nature-createurs/{id}")
    public ResponseEntity<NatureCreateurDTO> getNatureCreateur(@PathVariable Long id) {
        log.debug("REST request to get NatureCreateur : {}", id);
        Optional<NatureCreateurDTO> natureCreateurDTO = natureCreateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(natureCreateurDTO);
    }

    /**
     * {@code DELETE  /nature-createurs/:id} : delete the "id" natureCreateur.
     *
     * @param id the id of the natureCreateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nature-createurs/{id}")
    public ResponseEntity<Void> deleteNatureCreateur(@PathVariable Long id) {
        log.debug("REST request to delete NatureCreateur : {}", id);
        natureCreateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
