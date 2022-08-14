package mtn.hackaton.beveapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import mtn.hackaton.beveapp.repository.DonRepository;
import mtn.hackaton.beveapp.service.DonService;
import mtn.hackaton.beveapp.service.dto.DonDTO;
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
 * REST controller for managing {@link mtn.hackaton.beveapp.domain.Don}.
 */
@RestController
@RequestMapping("/api")
public class DonResource {

    private final Logger log = LoggerFactory.getLogger(DonResource.class);

    private static final String ENTITY_NAME = "don";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DonService donService;

    private final DonRepository donRepository;

    public DonResource(DonService donService, DonRepository donRepository) {
        this.donService = donService;
        this.donRepository = donRepository;
    }

    /**
     * {@code POST  /dons} : Create a new don.
     *
     * @param donDTO the donDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new donDTO, or with status {@code 400 (Bad Request)} if the don has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dons")
    public ResponseEntity<DonDTO> createDon(@RequestBody DonDTO donDTO) throws URISyntaxException {
        log.debug("REST request to save Don : {}", donDTO);
        if (donDTO.getId() != null) {
            throw new BadRequestAlertException("A new don cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DonDTO result = donService.save(donDTO);
        return ResponseEntity
            .created(new URI("/api/dons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dons/:id} : Updates an existing don.
     *
     * @param id the id of the donDTO to save.
     * @param donDTO the donDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donDTO,
     * or with status {@code 400 (Bad Request)} if the donDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the donDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dons/{id}")
    public ResponseEntity<DonDTO> updateDon(@PathVariable(value = "id", required = false) final Long id, @RequestBody DonDTO donDTO)
        throws URISyntaxException {
        log.debug("REST request to update Don : {}, {}", id, donDTO);
        if (donDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DonDTO result = donService.update(donDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /dons/:id} : Partial updates given fields of an existing don, field will ignore if it is null
     *
     * @param id the id of the donDTO to save.
     * @param donDTO the donDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated donDTO,
     * or with status {@code 400 (Bad Request)} if the donDTO is not valid,
     * or with status {@code 404 (Not Found)} if the donDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the donDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/dons/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DonDTO> partialUpdateDon(@PathVariable(value = "id", required = false) final Long id, @RequestBody DonDTO donDTO)
        throws URISyntaxException {
        log.debug("REST request to partial update Don partially : {}, {}", id, donDTO);
        if (donDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, donDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!donRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DonDTO> result = donService.partialUpdate(donDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, donDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dons} : get all the dons.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dons in body.
     */
    @GetMapping("/dons")
    public ResponseEntity<List<DonDTO>> getAllDons(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Dons");
        Page<DonDTO> page;
        if (eagerload) {
            page = donService.findAllWithEagerRelationships(pageable);
        } else {
            page = donService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dons/:id} : get the "id" don.
     *
     * @param id the id of the donDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the donDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dons/{id}")
    public ResponseEntity<DonDTO> getDon(@PathVariable Long id) {
        log.debug("REST request to get Don : {}", id);
        Optional<DonDTO> donDTO = donService.findOne(id);
        return ResponseUtil.wrapOrNotFound(donDTO);
    }

    /**
     * {@code DELETE  /dons/:id} : delete the "id" don.
     *
     * @param id the id of the donDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dons/{id}")
    public ResponseEntity<Void> deleteDon(@PathVariable Long id) {
        log.debug("REST request to delete Don : {}", id);
        donService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
