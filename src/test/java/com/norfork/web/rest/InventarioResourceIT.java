package com.norfork.web.rest;

import com.norfork.NorforkApp;
import com.norfork.domain.Inventario;
import com.norfork.domain.Producto;
import com.norfork.repository.InventarioRepository;
import com.norfork.service.InventarioService;
import com.norfork.service.dto.InventarioDTO;
import com.norfork.service.mapper.InventarioMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.norfork.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InventarioResource} REST controller.
 */
@SpringBootTest(classes = NorforkApp.class)
public class InventarioResourceIT {

    private static final BigDecimal DEFAULT_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_STOCK = new BigDecimal(2);

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private InventarioMapper inventarioMapper;

    @Autowired
    private InventarioService inventarioService;

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

    private MockMvc restInventarioMockMvc;

    private Inventario inventario;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InventarioResource inventarioResource = new InventarioResource(inventarioService);
        this.restInventarioMockMvc = MockMvcBuilders.standaloneSetup(inventarioResource)
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
    public static Inventario createEntity(EntityManager em) {
        Inventario inventario = new Inventario()
            .stock(DEFAULT_STOCK);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        inventario.setProducto(producto);
        return inventario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Inventario createUpdatedEntity(EntityManager em) {
        Inventario inventario = new Inventario()
            .stock(UPDATED_STOCK);
        // Add required entity
        Producto producto;
        if (TestUtil.findAll(em, Producto.class).isEmpty()) {
            producto = ProductoResourceIT.createUpdatedEntity(em);
            em.persist(producto);
            em.flush();
        } else {
            producto = TestUtil.findAll(em, Producto.class).get(0);
        }
        inventario.setProducto(producto);
        return inventario;
    }

    @BeforeEach
    public void initTest() {
        inventario = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventario() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isCreated());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate + 1);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getStock()).isEqualTo(DEFAULT_STOCK);
    }

    @Test
    @Transactional
    public void createInventarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventarioRepository.findAll().size();

        // Create the Inventario with an existing ID
        inventario.setId(1L);
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkStockIsRequired() throws Exception {
        int databaseSizeBeforeTest = inventarioRepository.findAll().size();
        // set the field null
        inventario.setStock(null);

        // Create the Inventario, which fails.
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        restInventarioMockMvc.perform(post("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isBadRequest());

        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllInventarios() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get all the inventarioList
        restInventarioMockMvc.perform(get("/api/inventarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventario.getId().intValue())))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK.intValue())));
    }
    
    @Test
    @Transactional
    public void getInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", inventario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inventario.getId().intValue()))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingInventario() throws Exception {
        // Get the inventario
        restInventarioMockMvc.perform(get("/api/inventarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Update the inventario
        Inventario updatedInventario = inventarioRepository.findById(inventario.getId()).get();
        // Disconnect from session so that the updates on updatedInventario are not directly saved in db
        em.detach(updatedInventario);
        updatedInventario
            .stock(UPDATED_STOCK);
        InventarioDTO inventarioDTO = inventarioMapper.toDto(updatedInventario);

        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isOk());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
        Inventario testInventario = inventarioList.get(inventarioList.size() - 1);
        assertThat(testInventario.getStock()).isEqualTo(UPDATED_STOCK);
    }

    @Test
    @Transactional
    public void updateNonExistingInventario() throws Exception {
        int databaseSizeBeforeUpdate = inventarioRepository.findAll().size();

        // Create the Inventario
        InventarioDTO inventarioDTO = inventarioMapper.toDto(inventario);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventarioMockMvc.perform(put("/api/inventarios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inventarioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inventario in the database
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventario() throws Exception {
        // Initialize the database
        inventarioRepository.saveAndFlush(inventario);

        int databaseSizeBeforeDelete = inventarioRepository.findAll().size();

        // Delete the inventario
        restInventarioMockMvc.perform(delete("/api/inventarios/{id}", inventario.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Inventario> inventarioList = inventarioRepository.findAll();
        assertThat(inventarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
