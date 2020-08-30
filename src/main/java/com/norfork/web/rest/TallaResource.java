package com.norfork.web.rest;

import com.norfork.service.TallaService;
import com.norfork.web.rest.errors.BadRequestAlertException;
import com.norfork.service.dto.TallaDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.norfork.domain.Talla}.
 */
@RestController
@RequestMapping("/api")
public class TallaResource {

    private final Logger log = LoggerFactory.getLogger(TallaResource.class);

    private static final String ENTITY_NAME = "talla";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TallaService tallaService;

    public TallaResource(TallaService tallaService) {
        this.tallaService = tallaService;
    }

    /**
     * {@code POST  /tallas} : Create a new talla.
     *
     * @param tallaDTO the tallaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tallaDTO, or with status {@code 400 (Bad Request)} if the talla has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tallas")
    public ResponseEntity<TallaDTO> createTalla(@Valid @RequestBody TallaDTO tallaDTO) throws URISyntaxException {
        log.debug("REST request to save Talla : {}", tallaDTO);
        if (tallaDTO.getId() != null) {
            throw new BadRequestAlertException("A new talla cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TallaDTO result = tallaService.save(tallaDTO);
        return ResponseEntity.created(new URI("/api/tallas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tallas} : Updates an existing talla.
     *
     * @param tallaDTO the tallaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tallaDTO,
     * or with status {@code 400 (Bad Request)} if the tallaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tallaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tallas")
    public ResponseEntity<TallaDTO> updateTalla(@Valid @RequestBody TallaDTO tallaDTO) throws URISyntaxException {
        log.debug("REST request to update Talla : {}", tallaDTO);
        if (tallaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TallaDTO result = tallaService.save(tallaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tallaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tallas} : get all the tallas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tallas in body.
     */
    @GetMapping("/tallas")
    public List<TallaDTO> getAllTallas() {
        log.debug("REST request to get all Tallas");
        return tallaService.findAll();
    }

    /**
     * {@code GET  /tallas/:id} : get the "id" talla.
     *
     * @param id the id of the tallaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tallaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tallas/{id}")
    public ResponseEntity<TallaDTO> getTalla(@PathVariable Long id) {
        log.debug("REST request to get Talla : {}", id);
        Optional<TallaDTO> tallaDTO = tallaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tallaDTO);
    }

    /**
     * {@code DELETE  /tallas/:id} : delete the "id" talla.
     *
     * @param id the id of the tallaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tallas/{id}")
    public ResponseEntity<Void> deleteTalla(@PathVariable Long id) {
        log.debug("REST request to delete Talla : {}", id);
        tallaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
