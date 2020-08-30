package com.norfork.service;

import com.norfork.domain.Talla;
import com.norfork.repository.TallaRepository;
import com.norfork.service.dto.TallaDTO;
import com.norfork.service.mapper.TallaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Talla}.
 */
@Service
@Transactional
public class TallaService {

    private final Logger log = LoggerFactory.getLogger(TallaService.class);

    private final TallaRepository tallaRepository;

    private final TallaMapper tallaMapper;

    public TallaService(TallaRepository tallaRepository, TallaMapper tallaMapper) {
        this.tallaRepository = tallaRepository;
        this.tallaMapper = tallaMapper;
    }

    /**
     * Save a talla.
     *
     * @param tallaDTO the entity to save.
     * @return the persisted entity.
     */
    public TallaDTO save(TallaDTO tallaDTO) {
        log.debug("Request to save Talla : {}", tallaDTO);
        Talla talla = tallaMapper.toEntity(tallaDTO);
        talla = tallaRepository.save(talla);
        return tallaMapper.toDto(talla);
    }

    /**
     * Get all the tallas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TallaDTO> findAll() {
        log.debug("Request to get all Tallas");
        return tallaRepository.findAll().stream()
            .map(tallaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one talla by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TallaDTO> findOne(Long id) {
        log.debug("Request to get Talla : {}", id);
        return tallaRepository.findById(id)
            .map(tallaMapper::toDto);
    }

    /**
     * Delete the talla by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Talla : {}", id);
        tallaRepository.deleteById(id);
    }
}
