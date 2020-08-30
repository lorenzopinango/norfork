package com.norfork.web.rest;

import com.norfork.NorforkApp;
import com.norfork.domain.Talla;
import com.norfork.repository.TallaRepository;
import com.norfork.service.TallaService;
import com.norfork.service.dto.TallaDTO;
import com.norfork.service.mapper.TallaMapper;
import com.norfork.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.norfork.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TallaResource} REST controller.
 */
@SpringBootTest(classes = NorforkApp.class)
public class TallaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TallaRepository tallaRepository;

    @Autowired
    private TallaMapper tallaMapper;

    @Autowired
    private TallaService tallaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTallaMockMvc;

    private Talla talla;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TallaResource tallaResource = new TallaResource(tallaService);
        this.restTallaMockMvc = MockMvcBuilders.standaloneSetup(tallaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Talla createEntity(EntityManager em) {
        Talla talla = new Talla()
            .nombre(DEFAULT_NOMBRE);
        return talla;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Talla createUpdatedEntity(EntityManager em) {
        Talla talla = new Talla()
            .nombre(UPDATED_NOMBRE);
        return talla;
    }

    @BeforeEach
    public void initTest() {
        talla = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalla() throws Exception {
        int databaseSizeBeforeCreate = tallaRepository.findAll().size();

        // Create the Talla
        TallaDTO tallaDTO = tallaMapper.toDto(talla);
        restTallaMockMvc.perform(post("/api/tallas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tallaDTO)))
            .andExpect(status().isCreated());

        // Validate the Talla in the database
        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeCreate + 1);
        Talla testTalla = tallaList.get(tallaList.size() - 1);
        assertThat(testTalla.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTallaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tallaRepository.findAll().size();

        // Create the Talla with an existing ID
        talla.setId(1L);
        TallaDTO tallaDTO = tallaMapper.toDto(talla);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTallaMockMvc.perform(post("/api/tallas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tallaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Talla in the database
        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tallaRepository.findAll().size();
        // set the field null
        talla.setNombre(null);

        // Create the Talla, which fails.
        TallaDTO tallaDTO = tallaMapper.toDto(talla);

        restTallaMockMvc.perform(post("/api/tallas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tallaDTO)))
            .andExpect(status().isBadRequest());

        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTallas() throws Exception {
        // Initialize the database
        tallaRepository.saveAndFlush(talla);

        // Get all the tallaList
        restTallaMockMvc.perform(get("/api/tallas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(talla.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));
    }
    
    @Test
    @Transactional
    public void getTalla() throws Exception {
        // Initialize the database
        tallaRepository.saveAndFlush(talla);

        // Get the talla
        restTallaMockMvc.perform(get("/api/tallas/{id}", talla.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(talla.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE));
    }

    @Test
    @Transactional
    public void getNonExistingTalla() throws Exception {
        // Get the talla
        restTallaMockMvc.perform(get("/api/tallas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalla() throws Exception {
        // Initialize the database
        tallaRepository.saveAndFlush(talla);

        int databaseSizeBeforeUpdate = tallaRepository.findAll().size();

        // Update the talla
        Talla updatedTalla = tallaRepository.findById(talla.getId()).get();
        // Disconnect from session so that the updates on updatedTalla are not directly saved in db
        em.detach(updatedTalla);
        updatedTalla
            .nombre(UPDATED_NOMBRE);
        TallaDTO tallaDTO = tallaMapper.toDto(updatedTalla);

        restTallaMockMvc.perform(put("/api/tallas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tallaDTO)))
            .andExpect(status().isOk());

        // Validate the Talla in the database
        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeUpdate);
        Talla testTalla = tallaList.get(tallaList.size() - 1);
        assertThat(testTalla.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTalla() throws Exception {
        int databaseSizeBeforeUpdate = tallaRepository.findAll().size();

        // Create the Talla
        TallaDTO tallaDTO = tallaMapper.toDto(talla);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTallaMockMvc.perform(put("/api/tallas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tallaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Talla in the database
        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTalla() throws Exception {
        // Initialize the database
        tallaRepository.saveAndFlush(talla);

        int databaseSizeBeforeDelete = tallaRepository.findAll().size();

        // Delete the talla
        restTallaMockMvc.perform(delete("/api/tallas/{id}", talla.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Talla> tallaList = tallaRepository.findAll();
        assertThat(tallaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
