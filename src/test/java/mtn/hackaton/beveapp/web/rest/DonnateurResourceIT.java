package mtn.hackaton.beveapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import mtn.hackaton.beveapp.IntegrationTest;
import mtn.hackaton.beveapp.domain.Donnateur;
import mtn.hackaton.beveapp.domain.enumeration.Forfait;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;
import mtn.hackaton.beveapp.repository.DonnateurRepository;
import mtn.hackaton.beveapp.service.dto.DonnateurDTO;
import mtn.hackaton.beveapp.service.mapper.DonnateurMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link DonnateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DonnateurResourceIT {

    private static final String DEFAULT_NOM_DE_FAMILLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DE_FAMILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_2 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.F;
    private static final Sexe UPDATED_SEXE = Sexe.M;

    private static final String DEFAULT_DATE_DE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final Forfait DEFAULT_FORFAIT = Forfait.AUJOURD_HUI;
    private static final Forfait UPDATED_FORFAIT = Forfait.JOUR;

    private static final String ENTITY_API_URL = "/api/donnateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DonnateurRepository donnateurRepository;

    @Autowired
    private DonnateurMapper donnateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDonnateurMockMvc;

    private Donnateur donnateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donnateur createEntity(EntityManager em) {
        Donnateur donnateur = new Donnateur()
            .nomDeFamille(DEFAULT_NOM_DE_FAMILLE)
            .prenom(DEFAULT_PRENOM)
            .contact1(DEFAULT_CONTACT_1)
            .contact2(DEFAULT_CONTACT_2)
            .email(DEFAULT_EMAIL)
            .sexe(DEFAULT_SEXE)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .pays(DEFAULT_PAYS)
            .dateDebut(DEFAULT_DATE_DEBUT)
            .forfait(DEFAULT_FORFAIT);
        return donnateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Donnateur createUpdatedEntity(EntityManager em) {
        Donnateur donnateur = new Donnateur()
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .email(UPDATED_EMAIL)
            .sexe(UPDATED_SEXE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .dateDebut(UPDATED_DATE_DEBUT)
            .forfait(UPDATED_FORFAIT);
        return donnateur;
    }

    @BeforeEach
    public void initTest() {
        donnateur = createEntity(em);
    }

    @Test
    @Transactional
    void createDonnateur() throws Exception {
        int databaseSizeBeforeCreate = donnateurRepository.findAll().size();
        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);
        restDonnateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donnateurDTO)))
            .andExpect(status().isCreated());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeCreate + 1);
        Donnateur testDonnateur = donnateurList.get(donnateurList.size() - 1);
        assertThat(testDonnateur.getNomDeFamille()).isEqualTo(DEFAULT_NOM_DE_FAMILLE);
        assertThat(testDonnateur.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testDonnateur.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testDonnateur.getContact2()).isEqualTo(DEFAULT_CONTACT_2);
        assertThat(testDonnateur.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDonnateur.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDonnateur.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testDonnateur.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testDonnateur.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDonnateur.getForfait()).isEqualTo(DEFAULT_FORFAIT);
    }

    @Test
    @Transactional
    void createDonnateurWithExistingId() throws Exception {
        // Create the Donnateur with an existing ID
        donnateur.setId(1L);
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        int databaseSizeBeforeCreate = donnateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDonnateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donnateurDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomDeFamilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = donnateurRepository.findAll().size();
        // set the field null
        donnateur.setNomDeFamille(null);

        // Create the Donnateur, which fails.
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        restDonnateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donnateurDTO)))
            .andExpect(status().isBadRequest());

        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContact1IsRequired() throws Exception {
        int databaseSizeBeforeTest = donnateurRepository.findAll().size();
        // set the field null
        donnateur.setContact1(null);

        // Create the Donnateur, which fails.
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        restDonnateurMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donnateurDTO)))
            .andExpect(status().isBadRequest());

        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDonnateurs() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        // Get all the donnateurList
        restDonnateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(donnateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDeFamille").value(hasItem(DEFAULT_NOM_DE_FAMILLE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].contact1").value(hasItem(DEFAULT_CONTACT_1)))
            .andExpect(jsonPath("$.[*].contact2").value(hasItem(DEFAULT_CONTACT_2)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())))
            .andExpect(jsonPath("$.[*].forfait").value(hasItem(DEFAULT_FORFAIT.toString())));
    }

    @Test
    @Transactional
    void getDonnateur() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        // Get the donnateur
        restDonnateurMockMvc
            .perform(get(ENTITY_API_URL_ID, donnateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(donnateur.getId().intValue()))
            .andExpect(jsonPath("$.nomDeFamille").value(DEFAULT_NOM_DE_FAMILLE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.contact1").value(DEFAULT_CONTACT_1))
            .andExpect(jsonPath("$.contact2").value(DEFAULT_CONTACT_2))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()))
            .andExpect(jsonPath("$.forfait").value(DEFAULT_FORFAIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingDonnateur() throws Exception {
        // Get the donnateur
        restDonnateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDonnateur() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();

        // Update the donnateur
        Donnateur updatedDonnateur = donnateurRepository.findById(donnateur.getId()).get();
        // Disconnect from session so that the updates on updatedDonnateur are not directly saved in db
        em.detach(updatedDonnateur);
        updatedDonnateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .email(UPDATED_EMAIL)
            .sexe(UPDATED_SEXE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .dateDebut(UPDATED_DATE_DEBUT)
            .forfait(UPDATED_FORFAIT);
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(updatedDonnateur);

        restDonnateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donnateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
        Donnateur testDonnateur = donnateurList.get(donnateurList.size() - 1);
        assertThat(testDonnateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testDonnateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDonnateur.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testDonnateur.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testDonnateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonnateur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDonnateur.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testDonnateur.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testDonnateur.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDonnateur.getForfait()).isEqualTo(UPDATED_FORFAIT);
    }

    @Test
    @Transactional
    void putNonExistingDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, donnateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(donnateurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDonnateurWithPatch() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();

        // Update the donnateur using partial update
        Donnateur partialUpdatedDonnateur = new Donnateur();
        partialUpdatedDonnateur.setId(donnateur.getId());

        partialUpdatedDonnateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact2(UPDATED_CONTACT_2)
            .email(UPDATED_EMAIL)
            .forfait(UPDATED_FORFAIT);

        restDonnateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonnateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonnateur))
            )
            .andExpect(status().isOk());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
        Donnateur testDonnateur = donnateurList.get(donnateurList.size() - 1);
        assertThat(testDonnateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testDonnateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDonnateur.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testDonnateur.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testDonnateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonnateur.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testDonnateur.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testDonnateur.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testDonnateur.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
        assertThat(testDonnateur.getForfait()).isEqualTo(UPDATED_FORFAIT);
    }

    @Test
    @Transactional
    void fullUpdateDonnateurWithPatch() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();

        // Update the donnateur using partial update
        Donnateur partialUpdatedDonnateur = new Donnateur();
        partialUpdatedDonnateur.setId(donnateur.getId());

        partialUpdatedDonnateur
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .email(UPDATED_EMAIL)
            .sexe(UPDATED_SEXE)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .dateDebut(UPDATED_DATE_DEBUT)
            .forfait(UPDATED_FORFAIT);

        restDonnateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDonnateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDonnateur))
            )
            .andExpect(status().isOk());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
        Donnateur testDonnateur = donnateurList.get(donnateurList.size() - 1);
        assertThat(testDonnateur.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testDonnateur.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testDonnateur.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testDonnateur.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testDonnateur.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDonnateur.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testDonnateur.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testDonnateur.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testDonnateur.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
        assertThat(testDonnateur.getForfait()).isEqualTo(UPDATED_FORFAIT);
    }

    @Test
    @Transactional
    void patchNonExistingDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, donnateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDonnateur() throws Exception {
        int databaseSizeBeforeUpdate = donnateurRepository.findAll().size();
        donnateur.setId(count.incrementAndGet());

        // Create the Donnateur
        DonnateurDTO donnateurDTO = donnateurMapper.toDto(donnateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDonnateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(donnateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Donnateur in the database
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDonnateur() throws Exception {
        // Initialize the database
        donnateurRepository.saveAndFlush(donnateur);

        int databaseSizeBeforeDelete = donnateurRepository.findAll().size();

        // Delete the donnateur
        restDonnateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, donnateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Donnateur> donnateurList = donnateurRepository.findAll();
        assertThat(donnateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
