package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import mtn.hackaton.beveapp.repository.DonnateurRepository;
import mtn.hackaton.beveapp.service.DonnateurService;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.Donnateur}.
 */
@RestController
@RequestMapping("/api")
public class DonnateurResource {

    private final Logger log = LoggerFactory.getLogger(DonnateurResource.class);

    private static final String ENTITY_NAME = "donnateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonnateurService donnateurService;

    private final DonnateurRepository donnateurRepository;

    public DonnateurResource(DonnateurService donnateurService, DonnateurRepository donnateurRepository) {
        this.donnateurService = donnateurService;
        this.donnateurRepository = donnateurRepository;
    }

    /**
     * {@code POST  /donnateurs} : Create a new donnateur.
     *
     * @param donnateurDTO the donnateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donnateurDTO, or with status {@code 400 (Bad Request)} if the donnateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/donnateurs")
    public ResponseEntity<DonnateurDTO> createDonnateur(@Valid @RequestBody DonnateurDTO donnateurDTO) throws URISyntaxException {
        log.debug("REST request to save Donnateur : {}", donnateurDTO);
        if (donnateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new donnateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonnateurDTO result = donnateurService.save(donnateurDTO);
        return ResponseEntity
            .created(new URI("/api/donnateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /donnateurs/:id} : Updates an existing donnateur.
     *
     * @param id the id of the donnateurDTO to save.
     * @param donnateurDTO the donnateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donnateurDTO,
     * or with status {@code 400 (Bad Request)} if the donnateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donnateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/donnateurs/{id}")
    public ResponseEntity<DonnateurDTO> updateDonnateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DonnateurDTO donnateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Donnateur : {}, {}", id, donnateurDTO);
        if (donnateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donnateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donnateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonnateurDTO result = donnateurService.update(donnateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donnateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /donnateurs/:id} : Partial updates given fields of an existing donnateur, field will ignore if it is null
     *
     * @param id the id of the donnateurDTO to save.
     * @param donnateurDTO the donnateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donnateurDTO,
     * or with status {@code 400 (Bad Request)} if the donnateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donnateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donnateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/donnateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonnateurDTO> partialUpdateDonnateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DonnateurDTO donnateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Donnateur partially : {}, {}", id, donnateurDTO);
        if (donnateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donnateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donnateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonnateurDTO> result = donnateurService.partialUpdate(donnateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donnateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /donnateurs} : get all the donnateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of donnateurs in body.
     */
    @GetMapping("/donnateurs")
    public ResponseEntity<List<DonnateurDTO>> getAllDonnateurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Donnateurs");
        Page<DonnateurDTO> page = donnateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /donnateurs/:id} : get the "id" donnateur.
     *
     * @param id the id of the donnateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donnateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/donnateurs/{id}")
    public ResponseEntity<DonnateurDTO> getDonnateur(@PathVariable Long id) {
        log.debug("REST request to get Donnateur : {}", id);
        Optional<DonnateurDTO> donnateurDTO = donnateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donnateurDTO);
    }

    /**
     * {@code DELETE  /donnateurs/:id} : delete the "id" donnateur.
     *
     * @param id the id of the donnateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/donnateurs/{id}")
    public ResponseEntity<Void> deleteDonnateur(@PathVariable Long id) {
        log.debug("REST request to delete Donnateur : {}", id);
        donnateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
