package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mtn.hackaton.beveapp.repository.CreateurAfricainRepository;
import mtn.hackaton.beveapp.service.CreateurAfricainService;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.CreateurAfricain}.
 */
@RestController
@RequestMapping("/api")
public class CreateurAfricainResource {

    private final Logger log = LoggerFactory.getLogger(CreateurAfricainResource.class);

    private static final String ENTITY_NAME = "createurAfricain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CreateurAfricainService createurAfricainService;

    private final CreateurAfricainRepository createurAfricainRepository;

    public CreateurAfricainResource(
        CreateurAfricainService createurAfricainService,
        CreateurAfricainRepository createurAfricainRepository
    ) {
        this.createurAfricainService = createurAfricainService;
        this.createurAfricainRepository = createurAfricainRepository;
    }

    /**
     * {@code POST  /createur-africains} : Create a new createurAfricain.
     *
     * @param createurAfricainDTO the createurAfricainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new createurAfricainDTO, or with status {@code 400 (Bad Request)} if the createurAfricain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/createur-africains")
    public ResponseEntity<CreateurAfricainDTO> createCreateurAfricain(@Valid @RequestBody CreateurAfricainDTO createurAfricainDTO)
        throws URISyntaxException {
        log.debug("REST request to save CreateurAfricain : {}", createurAfricainDTO);
        if (createurAfricainDTO.getId() != null) {
            throw new BadRequestAlertException("A new createurAfricain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CreateurAfricainDTO result = createurAfricainService.save(createurAfricainDTO);
        return ResponseEntity
            .created(new URI("/api/createur-africains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /createur-africains/:id} : Updates an existing createurAfricain.
     *
     * @param id the id of the createurAfricainDTO to save.
     * @param createurAfricainDTO the createurAfricainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createurAfricainDTO,
     * or with status {@code 400 (Bad Request)} if the createurAfricainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the createurAfricainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/createur-africains/{id}")
    public ResponseEntity<CreateurAfricainDTO> updateCreateurAfricain(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CreateurAfricainDTO createurAfricainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CreateurAfricain : {}, {}", id, createurAfricainDTO);
        if (createurAfricainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createurAfricainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!createurAfricainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CreateurAfricainDTO result = createurAfricainService.update(createurAfricainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createurAfricainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /createur-africains/:id} : Partial updates given fields of an existing createurAfricain, field will ignore if it is null
     *
     * @param id the id of the createurAfricainDTO to save.
     * @param createurAfricainDTO the createurAfricainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated createurAfricainDTO,
     * or with status {@code 400 (Bad Request)} if the createurAfricainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the createurAfricainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the createurAfricainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/createur-africains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CreateurAfricainDTO> partialUpdateCreateurAfricain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CreateurAfricainDTO createurAfricainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CreateurAfricain partially : {}, {}", id, createurAfricainDTO);
        if (createurAfricainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, createurAfricainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!createurAfricainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CreateurAfricainDTO> result = createurAfricainService.partialUpdate(createurAfricainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, createurAfricainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /createur-africains} : get all the createurAfricains.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of createurAfricains in body.
     */
    @GetMapping("/createur-africains")
    public ResponseEntity<List<CreateurAfricainDTO>> getAllCreateurAfricains(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of CreateurAfricains");
        Page<CreateurAfricainDTO> page;
        if (eagerload) {
            page = createurAfricainService.findAllWithEagerRelationships(pageable);
        } else {
            page = createurAfricainService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /createur-africains/:id} : get the "id" createurAfricain.
     *
     * @param id the id of the createurAfricainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the createurAfricainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/createur-africains/{id}")
    public ResponseEntity<CreateurAfricainDTO> getCreateurAfricain(@PathVariable Long id) {
        log.debug("REST request to get CreateurAfricain : {}", id);
        Optional<CreateurAfricainDTO> createurAfricainDTO = createurAfricainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(createurAfricainDTO);
    }

    /**
     * {@code DELETE  /createur-africains/:id} : delete the "id" createurAfricain.
     *
     * @param id the id of the createurAfricainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/createur-africains/{id}")
    public ResponseEntity<Void> deleteCreateurAfricain(@PathVariable Long id) {
        log.debug("REST request to delete CreateurAfricain : {}", id);
        createurAfricainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
