package mtn.hackaton.beveapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mtn.hackaton.beveapp.IntegrationTest;
import mtn.hackaton.beveapp.domain.Don;
import mtn.hackaton.beveapp.repository.DonRepository;
import mtn.hackaton.beveapp.service.DonService;
import mtn.hackaton.beveapp.service.dto.DonDTO;
import mtn.hackaton.beveapp.service.mapper.DonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DonResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DonResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DON = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DON = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/dons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonRepository donRepository;

    @Mock
    private DonRepository donRepositoryMock;

    @Autowired
    private DonMapper donMapper;

    @Mock
    private DonService donServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonMockMvc;

    private Don don;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Don createEntity(EntityManager em) {
        Don don = new Don().description(DEFAULT_DESCRIPTION).dateDon(DEFAULT_DATE_DON);
        return don;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Don createUpdatedEntity(EntityManager em) {
        Don don = new Don().description(UPDATED_DESCRIPTION).dateDon(UPDATED_DATE_DON);
        return don;
    }

    @BeforeEach
    public void initTest() {
        don = createEntity(em);
    }

    @Test
    @Transactional
    void createDon() throws Exception {
        int databaseSizeBeforeCreate = donRepository.findAll().size();
        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);
        restDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donDTO)))
            .andExpect(status().isCreated());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeCreate + 1);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDon.getDateDon()).isEqualTo(DEFAULT_DATE_DON);
    }

    @Test
    @Transactional
    void createDonWithExistingId() throws Exception {
        // Create the Don with an existing ID
        don.setId(1L);
        DonDTO donDTO = donMapper.toDto(don);

        int databaseSizeBeforeCreate = donRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDons() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        // Get all the donList
        restDonMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(don.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].dateDon").value(hasItem(DEFAULT_DATE_DON.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonsWithEagerRelationshipsIsEnabled() throws Exception {
        when(donServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(donServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDonsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(donServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDonMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(donRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        // Get the don
        restDonMockMvc
            .perform(get(ENTITY_API_URL_ID, don.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(don.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.dateDon").value(DEFAULT_DATE_DON.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDon() throws Exception {
        // Get the don
        restDonMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeUpdate = donRepository.findAll().size();

        // Update the don
        Don updatedDon = donRepository.findById(don.getId()).get();
        // Disconnect from session so that the updates on updatedDon are not directly saved in db
        em.detach(updatedDon);
        updatedDon.description(UPDATED_DESCRIPTION).dateDon(UPDATED_DATE_DON);
        DonDTO donDTO = donMapper.toDto(updatedDon);

        restDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donDTO))
            )
            .andExpect(status().isOk());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDon.getDateDon()).isEqualTo(UPDATED_DATE_DON);
    }

    @Test
    @Transactional
    void putNonExistingDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonWithPatch() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeUpdate = donRepository.findAll().size();

        // Update the don using partial update
        Don partialUpdatedDon = new Don();
        partialUpdatedDon.setId(don.getId());

        partialUpdatedDon.description(UPDATED_DESCRIPTION).dateDon(UPDATED_DATE_DON);

        restDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDon))
            )
            .andExpect(status().isOk());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDon.getDateDon()).isEqualTo(UPDATED_DATE_DON);
    }

    @Test
    @Transactional
    void fullUpdateDonWithPatch() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeUpdate = donRepository.findAll().size();

        // Update the don using partial update
        Don partialUpdatedDon = new Don();
        partialUpdatedDon.setId(don.getId());

        partialUpdatedDon.description(UPDATED_DESCRIPTION).dateDon(UPDATED_DATE_DON);

        restDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDon.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDon))
            )
            .andExpect(status().isOk());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
        Don testDon = donList.get(donList.size() - 1);
        assertThat(testDon.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDon.getDateDon()).isEqualTo(UPDATED_DATE_DON);
    }

    @Test
    @Transactional
    void patchNonExistingDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDon() throws Exception {
        int databaseSizeBeforeUpdate = donRepository.findAll().size();
        don.setId(count.incrementAndGet());

        // Create the Don
        DonDTO donDTO = donMapper.toDto(don);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Don in the database
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDon() throws Exception {
        // Initialize the database
        donRepository.saveAndFlush(don);

        int databaseSizeBeforeDelete = donRepository.findAll().size();

        // Delete the don
        restDonMockMvc.perform(delete(ENTITY_API_URL_ID, don.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Don> donList = donRepository.findAll();
        assertThat(donList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
