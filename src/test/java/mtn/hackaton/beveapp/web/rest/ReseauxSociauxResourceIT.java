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
import mtn.hackaton.beveapp.domain.ReseauxSociaux;
import mtn.hackaton.beveapp.repository.ReseauxSociauxRepository;
import mtn.hackaton.beveapp.service.dto.ReseauxSociauxDTO;
import mtn.hackaton.beveapp.service.mapper.ReseauxSociauxMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ReseauxSociauxResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ReseauxSociauxResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_CHAINE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_CHAINE = "BBBBBBBBBB";

    private static final String DEFAULT_LIEN_CHAINE = "AAAAAAAAAA";
    private static final String UPDATED_LIEN_CHAINE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/reseaux-sociauxes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ReseauxSociauxRepository reseauxSociauxRepository;

    @Autowired
    private ReseauxSociauxMapper reseauxSociauxMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restReseauxSociauxMockMvc;

    private ReseauxSociaux reseauxSociaux;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReseauxSociaux createEntity(EntityManager em) {
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux().nom(DEFAULT_NOM).nomChaine(DEFAULT_NOM_CHAINE).lienChaine(DEFAULT_LIEN_CHAINE);
        return reseauxSociaux;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReseauxSociaux createUpdatedEntity(EntityManager em) {
        ReseauxSociaux reseauxSociaux = new ReseauxSociaux().nom(UPDATED_NOM).nomChaine(UPDATED_NOM_CHAINE).lienChaine(UPDATED_LIEN_CHAINE);
        return reseauxSociaux;
    }

    @BeforeEach
    public void initTest() {
        reseauxSociaux = createEntity(em);
    }

    @Test
    @Transactional
    void createReseauxSociaux() throws Exception {
        int databaseSizeBeforeCreate = reseauxSociauxRepository.findAll().size();
        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);
        restReseauxSociauxMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeCreate + 1);
        ReseauxSociaux testReseauxSociaux = reseauxSociauxList.get(reseauxSociauxList.size() - 1);
        assertThat(testReseauxSociaux.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testReseauxSociaux.getNomChaine()).isEqualTo(DEFAULT_NOM_CHAINE);
        assertThat(testReseauxSociaux.getLienChaine()).isEqualTo(DEFAULT_LIEN_CHAINE);
    }

    @Test
    @Transactional
    void createReseauxSociauxWithExistingId() throws Exception {
        // Create the ReseauxSociaux with an existing ID
        reseauxSociaux.setId(1L);
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        int databaseSizeBeforeCreate = reseauxSociauxRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restReseauxSociauxMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = reseauxSociauxRepository.findAll().size();
        // set the field null
        reseauxSociaux.setNom(null);

        // Create the ReseauxSociaux, which fails.
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        restReseauxSociauxMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllReseauxSociauxes() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        // Get all the reseauxSociauxList
        restReseauxSociauxMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(reseauxSociaux.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].nomChaine").value(hasItem(DEFAULT_NOM_CHAINE)))
            .andExpect(jsonPath("$.[*].lienChaine").value(hasItem(DEFAULT_LIEN_CHAINE)));
    }

    @Test
    @Transactional
    void getReseauxSociaux() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        // Get the reseauxSociaux
        restReseauxSociauxMockMvc
            .perform(get(ENTITY_API_URL_ID, reseauxSociaux.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(reseauxSociaux.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.nomChaine").value(DEFAULT_NOM_CHAINE))
            .andExpect(jsonPath("$.lienChaine").value(DEFAULT_LIEN_CHAINE));
    }

    @Test
    @Transactional
    void getNonExistingReseauxSociaux() throws Exception {
        // Get the reseauxSociaux
        restReseauxSociauxMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewReseauxSociaux() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();

        // Update the reseauxSociaux
        ReseauxSociaux updatedReseauxSociaux = reseauxSociauxRepository.findById(reseauxSociaux.getId()).get();
        // Disconnect from session so that the updates on updatedReseauxSociaux are not directly saved in db
        em.detach(updatedReseauxSociaux);
        updatedReseauxSociaux.nom(UPDATED_NOM).nomChaine(UPDATED_NOM_CHAINE).lienChaine(UPDATED_LIEN_CHAINE);
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(updatedReseauxSociaux);

        restReseauxSociauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reseauxSociauxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isOk());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
        ReseauxSociaux testReseauxSociaux = reseauxSociauxList.get(reseauxSociauxList.size() - 1);
        assertThat(testReseauxSociaux.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testReseauxSociaux.getNomChaine()).isEqualTo(UPDATED_NOM_CHAINE);
        assertThat(testReseauxSociaux.getLienChaine()).isEqualTo(UPDATED_LIEN_CHAINE);
    }

    @Test
    @Transactional
    void putNonExistingReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, reseauxSociauxDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateReseauxSociauxWithPatch() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();

        // Update the reseauxSociaux using partial update
        ReseauxSociaux partialUpdatedReseauxSociaux = new ReseauxSociaux();
        partialUpdatedReseauxSociaux.setId(reseauxSociaux.getId());

        restReseauxSociauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReseauxSociaux.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReseauxSociaux))
            )
            .andExpect(status().isOk());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
        ReseauxSociaux testReseauxSociaux = reseauxSociauxList.get(reseauxSociauxList.size() - 1);
        assertThat(testReseauxSociaux.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testReseauxSociaux.getNomChaine()).isEqualTo(DEFAULT_NOM_CHAINE);
        assertThat(testReseauxSociaux.getLienChaine()).isEqualTo(DEFAULT_LIEN_CHAINE);
    }

    @Test
    @Transactional
    void fullUpdateReseauxSociauxWithPatch() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();

        // Update the reseauxSociaux using partial update
        ReseauxSociaux partialUpdatedReseauxSociaux = new ReseauxSociaux();
        partialUpdatedReseauxSociaux.setId(reseauxSociaux.getId());

        partialUpdatedReseauxSociaux.nom(UPDATED_NOM).nomChaine(UPDATED_NOM_CHAINE).lienChaine(UPDATED_LIEN_CHAINE);

        restReseauxSociauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedReseauxSociaux.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedReseauxSociaux))
            )
            .andExpect(status().isOk());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
        ReseauxSociaux testReseauxSociaux = reseauxSociauxList.get(reseauxSociauxList.size() - 1);
        assertThat(testReseauxSociaux.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testReseauxSociaux.getNomChaine()).isEqualTo(UPDATED_NOM_CHAINE);
        assertThat(testReseauxSociaux.getLienChaine()).isEqualTo(UPDATED_LIEN_CHAINE);
    }

    @Test
    @Transactional
    void patchNonExistingReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, reseauxSociauxDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamReseauxSociaux() throws Exception {
        int databaseSizeBeforeUpdate = reseauxSociauxRepository.findAll().size();
        reseauxSociaux.setId(count.incrementAndGet());

        // Create the ReseauxSociaux
        ReseauxSociauxDTO reseauxSociauxDTO = reseauxSociauxMapper.toDto(reseauxSociaux);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restReseauxSociauxMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(reseauxSociauxDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReseauxSociaux in the database
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteReseauxSociaux() throws Exception {
        // Initialize the database
        reseauxSociauxRepository.saveAndFlush(reseauxSociaux);

        int databaseSizeBeforeDelete = reseauxSociauxRepository.findAll().size();

        // Delete the reseauxSociaux
        restReseauxSociauxMockMvc
            .perform(delete(ENTITY_API_URL_ID, reseauxSociaux.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReseauxSociaux> reseauxSociauxList = reseauxSociauxRepository.findAll();
        assertThat(reseauxSociauxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
