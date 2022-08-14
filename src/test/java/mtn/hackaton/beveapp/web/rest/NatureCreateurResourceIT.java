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
import mtn.hackaton.beveapp.domain.NatureCreateur;
import mtn.hackaton.beveapp.repository.NatureCreateurRepository;
import mtn.hackaton.beveapp.service.dto.NatureCreateurDTO;
import mtn.hackaton.beveapp.service.mapper.NatureCreateurMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link NatureCreateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class NatureCreateurResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/nature-createurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private NatureCreateurRepository natureCreateurRepository;

    @Autowired
    private NatureCreateurMapper natureCreateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNatureCreateurMockMvc;

    private NatureCreateur natureCreateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureCreateur createEntity(EntityManager em) {
        NatureCreateur natureCreateur = new NatureCreateur().type(DEFAULT_TYPE).description(DEFAULT_DESCRIPTION);
        return natureCreateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NatureCreateur createUpdatedEntity(EntityManager em) {
        NatureCreateur natureCreateur = new NatureCreateur().type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);
        return natureCreateur;
    }

    @BeforeEach
    public void initTest() {
        natureCreateur = createEntity(em);
    }

    @Test
    @Transactional
    void createNatureCreateur() throws Exception {
        int databaseSizeBeforeCreate = natureCreateurRepository.findAll().size();
        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);
        restNatureCreateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeCreate + 1);
        NatureCreateur testNatureCreateur = natureCreateurList.get(natureCreateurList.size() - 1);
        assertThat(testNatureCreateur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNatureCreateur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createNatureCreateurWithExistingId() throws Exception {
        // Create the NatureCreateur with an existing ID
        natureCreateur.setId(1L);
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        int databaseSizeBeforeCreate = natureCreateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restNatureCreateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllNatureCreateurs() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        // Get all the natureCreateurList
        restNatureCreateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(natureCreateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getNatureCreateur() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        // Get the natureCreateur
        restNatureCreateurMockMvc
            .perform(get(ENTITY_API_URL_ID, natureCreateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(natureCreateur.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingNatureCreateur() throws Exception {
        // Get the natureCreateur
        restNatureCreateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewNatureCreateur() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();

        // Update the natureCreateur
        NatureCreateur updatedNatureCreateur = natureCreateurRepository.findById(natureCreateur.getId()).get();
        // Disconnect from session so that the updates on updatedNatureCreateur are not directly saved in db
        em.detach(updatedNatureCreateur);
        updatedNatureCreateur.type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(updatedNatureCreateur);

        restNatureCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureCreateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
        NatureCreateur testNatureCreateur = natureCreateurList.get(natureCreateurList.size() - 1);
        assertThat(testNatureCreateur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNatureCreateur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, natureCreateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateNatureCreateurWithPatch() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();

        // Update the natureCreateur using partial update
        NatureCreateur partialUpdatedNatureCreateur = new NatureCreateur();
        partialUpdatedNatureCreateur.setId(natureCreateur.getId());

        restNatureCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureCreateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureCreateur))
            )
            .andExpect(status().isOk());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
        NatureCreateur testNatureCreateur = natureCreateurList.get(natureCreateurList.size() - 1);
        assertThat(testNatureCreateur.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNatureCreateur.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateNatureCreateurWithPatch() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();

        // Update the natureCreateur using partial update
        NatureCreateur partialUpdatedNatureCreateur = new NatureCreateur();
        partialUpdatedNatureCreateur.setId(natureCreateur.getId());

        partialUpdatedNatureCreateur.type(UPDATED_TYPE).description(UPDATED_DESCRIPTION);

        restNatureCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedNatureCreateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedNatureCreateur))
            )
            .andExpect(status().isOk());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
        NatureCreateur testNatureCreateur = natureCreateurList.get(natureCreateurList.size() - 1);
        assertThat(testNatureCreateur.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNatureCreateur.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, natureCreateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamNatureCreateur() throws Exception {
        int databaseSizeBeforeUpdate = natureCreateurRepository.findAll().size();
        natureCreateur.setId(count.incrementAndGet());

        // Create the NatureCreateur
        NatureCreateurDTO natureCreateurDTO = natureCreateurMapper.toDto(natureCreateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restNatureCreateurMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(natureCreateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the NatureCreateur in the database
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteNatureCreateur() throws Exception {
        // Initialize the database
        natureCreateurRepository.saveAndFlush(natureCreateur);

        int databaseSizeBeforeDelete = natureCreateurRepository.findAll().size();

        // Delete the natureCreateur
        restNatureCreateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, natureCreateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NatureCreateur> natureCreateurList = natureCreateurRepository.findAll();
        assertThat(natureCreateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
