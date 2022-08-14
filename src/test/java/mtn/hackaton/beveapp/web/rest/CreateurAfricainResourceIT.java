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
import mtn.hackaton.beveapp.domain.CreateurAfricain;
import mtn.hackaton.beveapp.domain.enumeration.Sexe;
import mtn.hackaton.beveapp.domain.enumeration.SituationMatrimoniale;
import mtn.hackaton.beveapp.repository.CreateurAfricainRepository;
import mtn.hackaton.beveapp.service.dto.CreateurAfricainDTO;
import mtn.hackaton.beveapp.service.mapper.CreateurAfricainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CreateurAfricainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CreateurAfricainResourceIT {

    private static final String DEFAULT_NOM_DE_FAMILLE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_DE_FAMILLE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_LABEL = "AAAAAAAAAA";
    private static final String UPDATED_LABEL = "BBBBBBBBBB";

    private static final String DEFAULT_SURNOM = "AAAAAAAAAA";
    private static final String UPDATED_SURNOM = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_2 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_2 = "BBBBBBBBBB";

    private static final Sexe DEFAULT_SEXE = Sexe.F;
    private static final Sexe UPDATED_SEXE = Sexe.M;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_PAYS = "AAAAAAAAAA";
    private static final String UPDATED_PAYS = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final SituationMatrimoniale DEFAULT_SITUATION_MATRIMONIALE = SituationMatrimoniale.CELIBATAIRE;
    private static final SituationMatrimoniale UPDATED_SITUATION_MATRIMONIALE = SituationMatrimoniale.FIANCE;

    private static final LocalDate DEFAULT_DATE_DEBUT = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_DEBUT = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/createur-africains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CreateurAfricainRepository createurAfricainRepository;

    @Autowired
    private CreateurAfricainMapper createurAfricainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCreateurAfricainMockMvc;

    private CreateurAfricain createurAfricain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreateurAfricain createEntity(EntityManager em) {
        CreateurAfricain createurAfricain = new CreateurAfricain()
            .nomDeFamille(DEFAULT_NOM_DE_FAMILLE)
            .prenom(DEFAULT_PRENOM)
            .label(DEFAULT_LABEL)
            .surnom(DEFAULT_SURNOM)
            .contact1(DEFAULT_CONTACT_1)
            .contact2(DEFAULT_CONTACT_2)
            .sexe(DEFAULT_SEXE)
            .email(DEFAULT_EMAIL)
            .dateDeNaissance(DEFAULT_DATE_DE_NAISSANCE)
            .pays(DEFAULT_PAYS)
            .ville(DEFAULT_VILLE)
            .adresse(DEFAULT_ADRESSE)
            .situationMatrimoniale(DEFAULT_SITUATION_MATRIMONIALE)
            .dateDebut(DEFAULT_DATE_DEBUT);
        return createurAfricain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CreateurAfricain createUpdatedEntity(EntityManager em) {
        CreateurAfricain createurAfricain = new CreateurAfricain()
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .label(UPDATED_LABEL)
            .surnom(UPDATED_SURNOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE)
            .dateDebut(UPDATED_DATE_DEBUT);
        return createurAfricain;
    }

    @BeforeEach
    public void initTest() {
        createurAfricain = createEntity(em);
    }

    @Test
    @Transactional
    void createCreateurAfricain() throws Exception {
        int databaseSizeBeforeCreate = createurAfricainRepository.findAll().size();
        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);
        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeCreate + 1);
        CreateurAfricain testCreateurAfricain = createurAfricainList.get(createurAfricainList.size() - 1);
        assertThat(testCreateurAfricain.getNomDeFamille()).isEqualTo(DEFAULT_NOM_DE_FAMILLE);
        assertThat(testCreateurAfricain.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCreateurAfricain.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCreateurAfricain.getSurnom()).isEqualTo(DEFAULT_SURNOM);
        assertThat(testCreateurAfricain.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testCreateurAfricain.getContact2()).isEqualTo(DEFAULT_CONTACT_2);
        assertThat(testCreateurAfricain.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testCreateurAfricain.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testCreateurAfricain.getDateDeNaissance()).isEqualTo(DEFAULT_DATE_DE_NAISSANCE);
        assertThat(testCreateurAfricain.getPays()).isEqualTo(DEFAULT_PAYS);
        assertThat(testCreateurAfricain.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testCreateurAfricain.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testCreateurAfricain.getSituationMatrimoniale()).isEqualTo(DEFAULT_SITUATION_MATRIMONIALE);
        assertThat(testCreateurAfricain.getDateDebut()).isEqualTo(DEFAULT_DATE_DEBUT);
    }

    @Test
    @Transactional
    void createCreateurAfricainWithExistingId() throws Exception {
        // Create the CreateurAfricain with an existing ID
        createurAfricain.setId(1L);
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        int databaseSizeBeforeCreate = createurAfricainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomDeFamilleIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setNomDeFamille(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setPrenom(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLabelIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setLabel(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkContact1IsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setContact1(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setSexe(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setEmail(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaysIsRequired() throws Exception {
        int databaseSizeBeforeTest = createurAfricainRepository.findAll().size();
        // set the field null
        createurAfricain.setPays(null);

        // Create the CreateurAfricain, which fails.
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCreateurAfricains() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        // Get all the createurAfricainList
        restCreateurAfricainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(createurAfricain.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomDeFamille").value(hasItem(DEFAULT_NOM_DE_FAMILLE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].label").value(hasItem(DEFAULT_LABEL)))
            .andExpect(jsonPath("$.[*].surnom").value(hasItem(DEFAULT_SURNOM)))
            .andExpect(jsonPath("$.[*].contact1").value(hasItem(DEFAULT_CONTACT_1)))
            .andExpect(jsonPath("$.[*].contact2").value(hasItem(DEFAULT_CONTACT_2)))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].dateDeNaissance").value(hasItem(DEFAULT_DATE_DE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].pays").value(hasItem(DEFAULT_PAYS)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].situationMatrimoniale").value(hasItem(DEFAULT_SITUATION_MATRIMONIALE.toString())))
            .andExpect(jsonPath("$.[*].dateDebut").value(hasItem(DEFAULT_DATE_DEBUT.toString())));
    }

    @Test
    @Transactional
    void getCreateurAfricain() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        // Get the createurAfricain
        restCreateurAfricainMockMvc
            .perform(get(ENTITY_API_URL_ID, createurAfricain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(createurAfricain.getId().intValue()))
            .andExpect(jsonPath("$.nomDeFamille").value(DEFAULT_NOM_DE_FAMILLE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.label").value(DEFAULT_LABEL))
            .andExpect(jsonPath("$.surnom").value(DEFAULT_SURNOM))
            .andExpect(jsonPath("$.contact1").value(DEFAULT_CONTACT_1))
            .andExpect(jsonPath("$.contact2").value(DEFAULT_CONTACT_2))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.dateDeNaissance").value(DEFAULT_DATE_DE_NAISSANCE))
            .andExpect(jsonPath("$.pays").value(DEFAULT_PAYS))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.situationMatrimoniale").value(DEFAULT_SITUATION_MATRIMONIALE.toString()))
            .andExpect(jsonPath("$.dateDebut").value(DEFAULT_DATE_DEBUT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCreateurAfricain() throws Exception {
        // Get the createurAfricain
        restCreateurAfricainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCreateurAfricain() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();

        // Update the createurAfricain
        CreateurAfricain updatedCreateurAfricain = createurAfricainRepository.findById(createurAfricain.getId()).get();
        // Disconnect from session so that the updates on updatedCreateurAfricain are not directly saved in db
        em.detach(updatedCreateurAfricain);
        updatedCreateurAfricain
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .label(UPDATED_LABEL)
            .surnom(UPDATED_SURNOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE)
            .dateDebut(UPDATED_DATE_DEBUT);
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(updatedCreateurAfricain);

        restCreateurAfricainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, createurAfricainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isOk());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
        CreateurAfricain testCreateurAfricain = createurAfricainList.get(createurAfricainList.size() - 1);
        assertThat(testCreateurAfricain.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testCreateurAfricain.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCreateurAfricain.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCreateurAfricain.getSurnom()).isEqualTo(UPDATED_SURNOM);
        assertThat(testCreateurAfricain.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testCreateurAfricain.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testCreateurAfricain.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testCreateurAfricain.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCreateurAfricain.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testCreateurAfricain.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testCreateurAfricain.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testCreateurAfricain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCreateurAfricain.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
        assertThat(testCreateurAfricain.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    void putNonExistingCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, createurAfricainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCreateurAfricainWithPatch() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();

        // Update the createurAfricain using partial update
        CreateurAfricain partialUpdatedCreateurAfricain = new CreateurAfricain();
        partialUpdatedCreateurAfricain.setId(createurAfricain.getId());

        partialUpdatedCreateurAfricain
            .surnom(UPDATED_SURNOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .email(UPDATED_EMAIL)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE)
            .dateDebut(UPDATED_DATE_DEBUT);

        restCreateurAfricainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreateurAfricain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreateurAfricain))
            )
            .andExpect(status().isOk());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
        CreateurAfricain testCreateurAfricain = createurAfricainList.get(createurAfricainList.size() - 1);
        assertThat(testCreateurAfricain.getNomDeFamille()).isEqualTo(DEFAULT_NOM_DE_FAMILLE);
        assertThat(testCreateurAfricain.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testCreateurAfricain.getLabel()).isEqualTo(DEFAULT_LABEL);
        assertThat(testCreateurAfricain.getSurnom()).isEqualTo(UPDATED_SURNOM);
        assertThat(testCreateurAfricain.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testCreateurAfricain.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testCreateurAfricain.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testCreateurAfricain.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCreateurAfricain.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testCreateurAfricain.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testCreateurAfricain.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testCreateurAfricain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCreateurAfricain.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
        assertThat(testCreateurAfricain.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    void fullUpdateCreateurAfricainWithPatch() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();

        // Update the createurAfricain using partial update
        CreateurAfricain partialUpdatedCreateurAfricain = new CreateurAfricain();
        partialUpdatedCreateurAfricain.setId(createurAfricain.getId());

        partialUpdatedCreateurAfricain
            .nomDeFamille(UPDATED_NOM_DE_FAMILLE)
            .prenom(UPDATED_PRENOM)
            .label(UPDATED_LABEL)
            .surnom(UPDATED_SURNOM)
            .contact1(UPDATED_CONTACT_1)
            .contact2(UPDATED_CONTACT_2)
            .sexe(UPDATED_SEXE)
            .email(UPDATED_EMAIL)
            .dateDeNaissance(UPDATED_DATE_DE_NAISSANCE)
            .pays(UPDATED_PAYS)
            .ville(UPDATED_VILLE)
            .adresse(UPDATED_ADRESSE)
            .situationMatrimoniale(UPDATED_SITUATION_MATRIMONIALE)
            .dateDebut(UPDATED_DATE_DEBUT);

        restCreateurAfricainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCreateurAfricain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCreateurAfricain))
            )
            .andExpect(status().isOk());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
        CreateurAfricain testCreateurAfricain = createurAfricainList.get(createurAfricainList.size() - 1);
        assertThat(testCreateurAfricain.getNomDeFamille()).isEqualTo(UPDATED_NOM_DE_FAMILLE);
        assertThat(testCreateurAfricain.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testCreateurAfricain.getLabel()).isEqualTo(UPDATED_LABEL);
        assertThat(testCreateurAfricain.getSurnom()).isEqualTo(UPDATED_SURNOM);
        assertThat(testCreateurAfricain.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testCreateurAfricain.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testCreateurAfricain.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testCreateurAfricain.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testCreateurAfricain.getDateDeNaissance()).isEqualTo(UPDATED_DATE_DE_NAISSANCE);
        assertThat(testCreateurAfricain.getPays()).isEqualTo(UPDATED_PAYS);
        assertThat(testCreateurAfricain.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testCreateurAfricain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testCreateurAfricain.getSituationMatrimoniale()).isEqualTo(UPDATED_SITUATION_MATRIMONIALE);
        assertThat(testCreateurAfricain.getDateDebut()).isEqualTo(UPDATED_DATE_DEBUT);
    }

    @Test
    @Transactional
    void patchNonExistingCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, createurAfricainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCreateurAfricain() throws Exception {
        int databaseSizeBeforeUpdate = createurAfricainRepository.findAll().size();
        createurAfricain.setId(count.incrementAndGet());

        // Create the CreateurAfricain
        CreateurAfricainDTO createurAfricainDTO = createurAfricainMapper.toDto(createurAfricain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCreateurAfricainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(createurAfricainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CreateurAfricain in the database
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCreateurAfricain() throws Exception {
        // Initialize the database
        createurAfricainRepository.saveAndFlush(createurAfricain);

        int databaseSizeBeforeDelete = createurAfricainRepository.findAll().size();

        // Delete the createurAfricain
        restCreateurAfricainMockMvc
            .perform(delete(ENTITY_API_URL_ID, createurAfricain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CreateurAfricain> createurAfricainList = createurAfricainRepository.findAll();
        assertThat(createurAfricainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
