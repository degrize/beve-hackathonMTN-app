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
import mtn.hackaton.beveapp.domain.Inspiration;
import mtn.hackaton.beveapp.repository.InspirationRepository;
import mtn.hackaton.beveapp.service.dto.InspirationDTO;
import mtn.hackaton.beveapp.service.mapper.InspirationMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link InspirationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class InspirationResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_ARTICLE_INSPIRATION = "AAAAAAAAAA";
    private static final String UPDATED_ARTICLE_INSPIRATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/inspirations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private InspirationRepository inspirationRepository;

    @Autowired
    private InspirationMapper inspirationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInspirationMockMvc;

    private Inspiration inspiration;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspiration createEntity(EntityManager em) {
        Inspiration inspiration = new Inspiration().nom(DEFAULT_NOM).articleInspiration(DEFAULT_ARTICLE_INSPIRATION);
        return inspiration;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inspiration createUpdatedEntity(EntityManager em) {
        Inspiration inspiration = new Inspiration().nom(UPDATED_NOM).articleInspiration(UPDATED_ARTICLE_INSPIRATION);
        return inspiration;
    }

    @BeforeEach
    public void initTest() {
        inspiration = createEntity(em);
    }

    @Test
    @Transactional
    void createInspiration() throws Exception {
        int databaseSizeBeforeCreate = inspirationRepository.findAll().size();
        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);
        restInspirationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeCreate + 1);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testInspiration.getArticleInspiration()).isEqualTo(DEFAULT_ARTICLE_INSPIRATION);
    }

    @Test
    @Transactional
    void createInspirationWithExistingId() throws Exception {
        // Create the Inspiration with an existing ID
        inspiration.setId(1L);
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        int databaseSizeBeforeCreate = inspirationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspirationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = inspirationRepository.findAll().size();
        // set the field null
        inspiration.setNom(null);

        // Create the Inspiration, which fails.
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        restInspirationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllInspirations() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        // Get all the inspirationList
        restInspirationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspiration.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].articleInspiration").value(hasItem(DEFAULT_ARTICLE_INSPIRATION)));
    }

    @Test
    @Transactional
    void getInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        // Get the inspiration
        restInspirationMockMvc
            .perform(get(ENTITY_API_URL_ID, inspiration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inspiration.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.articleInspiration").value(DEFAULT_ARTICLE_INSPIRATION));
    }

    @Test
    @Transactional
    void getNonExistingInspiration() throws Exception {
        // Get the inspiration
        restInspirationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();

        // Update the inspiration
        Inspiration updatedInspiration = inspirationRepository.findById(inspiration.getId()).get();
        // Disconnect from session so that the updates on updatedInspiration are not directly saved in db
        em.detach(updatedInspiration);
        updatedInspiration.nom(UPDATED_NOM).articleInspiration(UPDATED_ARTICLE_INSPIRATION);
        InspirationDTO inspirationDTO = inspirationMapper.toDto(updatedInspiration);

        restInspirationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspirationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testInspiration.getArticleInspiration()).isEqualTo(UPDATED_ARTICLE_INSPIRATION);
    }

    @Test
    @Transactional
    void putNonExistingInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, inspirationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(inspirationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateInspirationWithPatch() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();

        // Update the inspiration using partial update
        Inspiration partialUpdatedInspiration = new Inspiration();
        partialUpdatedInspiration.setId(inspiration.getId());

        restInspirationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspiration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspiration))
            )
            .andExpect(status().isOk());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testInspiration.getArticleInspiration()).isEqualTo(DEFAULT_ARTICLE_INSPIRATION);
    }

    @Test
    @Transactional
    void fullUpdateInspirationWithPatch() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();

        // Update the inspiration using partial update
        Inspiration partialUpdatedInspiration = new Inspiration();
        partialUpdatedInspiration.setId(inspiration.getId());

        partialUpdatedInspiration.nom(UPDATED_NOM).articleInspiration(UPDATED_ARTICLE_INSPIRATION);

        restInspirationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedInspiration.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedInspiration))
            )
            .andExpect(status().isOk());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testInspiration.getArticleInspiration()).isEqualTo(UPDATED_ARTICLE_INSPIRATION);
    }

    @Test
    @Transactional
    void patchNonExistingInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, inspirationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();
        inspiration.setId(count.incrementAndGet());

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restInspirationMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(inspirationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        int databaseSizeBeforeDelete = inspirationRepository.findAll().size();

        // Delete the inspiration
        restInspirationMockMvc
            .perform(delete(ENTITY_API_URL_ID, inspiration.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
