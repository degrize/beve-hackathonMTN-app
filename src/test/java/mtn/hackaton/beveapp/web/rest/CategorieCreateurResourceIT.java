package mtn.hackaton.beveapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mtn.hackaton.beveapp.IntegrationTest;
import mtn.hackaton.beveapp.domain.CategorieCreateur;
import mtn.hackaton.beveapp.repository.CategorieCreateurRepository;
import mtn.hackaton.beveapp.service.dto.CategorieCreateurDTO;
import mtn.hackaton.beveapp.service.mapper.CategorieCreateurMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CategorieCreateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CategorieCreateurResourceIT {

    private static final String DEFAULT_CATEGORIE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORIE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/categorie-createurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CategorieCreateurRepository categorieCreateurRepository;

    @Autowired
    private CategorieCreateurMapper categorieCreateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCategorieCreateurMockMvc;

    private CategorieCreateur categorieCreateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCreateur createEntity(EntityManager em) {
        CategorieCreateur categorieCreateur = new CategorieCreateur().categorie(DEFAULT_CATEGORIE).description(DEFAULT_DESCRIPTION);
        return categorieCreateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CategorieCreateur createUpdatedEntity(EntityManager em) {
        CategorieCreateur categorieCreateur = new CategorieCreateur().categorie(UPDATED_CATEGORIE).description(UPDATED_DESCRIPTION);
        return categorieCreateur;
    }

    @BeforeEach
    public void initTest() {
        categorieCreateur = createEntity(em);
    }

    @Test
    @Transactional
    void createCategorieCreateur() throws Exception {
        int databaseSizeBeforeCreate = categorieCreateurRepository.findAll().size();
        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);
        restCategorieCreateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeCreate + 1);
        CategorieCreateur testCategorieCreateur = categorieCreateurList.get(categorieCreateurList.size() - 1);
        assertThat(testCategorieCreateur.getCategorie()).isEqualTo(DEFAULT_CATEGORIE);
        assertThat(testCategorieCreateur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createCategorieCreateurWithExistingId() throws Exception {
        // Create the CategorieCreateur with an existing ID
        categorieCreateur.setId(1L);
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        int databaseSizeBeforeCreate = categorieCreateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCategorieCreateurMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCategorieCreateurs() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        // Get all the categorieCreateurList
        restCategorieCreateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(categorieCreateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].categorie").value(hasItem(DEFAULT_CATEGORIE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getCategorieCreateur() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        // Get the categorieCreateur
        restCategorieCreateurMockMvc
            .perform(get(ENTITY_API_URL_ID, categorieCreateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(categorieCreateur.getId().intValue()))
            .andExpect(jsonPath("$.categorie").value(DEFAULT_CATEGORIE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingCategorieCreateur() throws Exception {
        // Get the categorieCreateur
        restCategorieCreateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCategorieCreateur() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();

        // Update the categorieCreateur
        CategorieCreateur updatedCategorieCreateur = categorieCreateurRepository.findById(categorieCreateur.getId()).get();
        // Disconnect from session so that the updates on updatedCategorieCreateur are not directly saved in db
        em.detach(updatedCategorieCreateur);
        updatedCategorieCreateur.categorie(UPDATED_CATEGORIE).description(UPDATED_DESCRIPTION);
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(updatedCategorieCreateur);

        restCategorieCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieCreateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
        CategorieCreateur testCategorieCreateur = categorieCreateurList.get(categorieCreateurList.size() - 1);
        assertThat(testCategorieCreateur.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testCategorieCreateur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, categorieCreateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCategorieCreateurWithPatch() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();

        // Update the categorieCreateur using partial update
        CategorieCreateur partialUpdatedCategorieCreateur = new CategorieCreateur();
        partialUpdatedCategorieCreateur.setId(categorieCreateur.getId());

        partialUpdatedCategorieCreateur.categorie(UPDATED_CATEGORIE);

        restCategorieCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCreateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCreateur))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
        CategorieCreateur testCategorieCreateur = categorieCreateurList.get(categorieCreateurList.size() - 1);
        assertThat(testCategorieCreateur.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testCategorieCreateur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateCategorieCreateurWithPatch() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();

        // Update the categorieCreateur using partial update
        CategorieCreateur partialUpdatedCategorieCreateur = new CategorieCreateur();
        partialUpdatedCategorieCreateur.setId(categorieCreateur.getId());

        partialUpdatedCategorieCreateur.categorie(UPDATED_CATEGORIE).description(UPDATED_DESCRIPTION);

        restCategorieCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCategorieCreateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCategorieCreateur))
            )
            .andExpect(status().isOk());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
        CategorieCreateur testCategorieCreateur = categorieCreateurList.get(categorieCreateurList.size() - 1);
        assertThat(testCategorieCreateur.getCategorie()).isEqualTo(UPDATED_CATEGORIE);
        assertThat(testCategorieCreateur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, categorieCreateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCategorieCreateur() throws Exception {
        int databaseSizeBeforeUpdate = categorieCreateurRepository.findAll().size();
        categorieCreateur.setId(count.incrementAndGet());

        // Create the CategorieCreateur
        CategorieCreateurDTO categorieCreateurDTO = categorieCreateurMapper.toDto(categorieCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCategorieCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(categorieCreateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CategorieCreateur in the database
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCategorieCreateur() throws Exception {
        // Initialize the database
        categorieCreateurRepository.saveAndFlush(categorieCreateur);

        int databaseSizeBeforeDelete = categorieCreateurRepository.findAll().size();

        // Delete the categorieCreateur
        restCategorieCreateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, categorieCreateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CategorieCreateur> categorieCreateurList = categorieCreateurRepository.findAll();
        assertThat(categorieCreateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
