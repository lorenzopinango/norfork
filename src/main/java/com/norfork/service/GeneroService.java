package com.norfork.service;

import com.norfork.domain.Genero;
import com.norfork.repository.GeneroRepository;
import com.norfork.service.dto.GeneroDTO;
import com.norfork.service.mapper.GeneroMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Genero}.
 */
@Service
@Transactional
public class GeneroService {

    private final Logger log = LoggerFactory.getLogger(GeneroService.class);

    private final GeneroRepository generoRepository;

    private final GeneroMapper generoMapper;

    public GeneroService(GeneroRepository generoRepository, GeneroMapper generoMapper) {
        this.generoRepository = generoRepository;
        this.generoMapper = generoMapper;
    }

    /**
     * Save a genero.
     *
     * @param generoDTO the entity to save.
     * @return the persisted entity.
     */
    public GeneroDTO save(GeneroDTO generoDTO) {
        log.debug("Request to save Genero : {}", generoDTO);
        Genero genero = generoMapper.toEntity(generoDTO);
        genero = generoRepository.save(genero);
        return generoMapper.toDto(genero);
    }

    /**
     * Get all the generos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GeneroDTO> findAll() {
        log.debug("Request to get all Generos");
        return generoRepository.findAll().stream()
            .map(generoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one genero by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GeneroDTO> findOne(Long id) {
        log.debug("Request to get Genero : {}", id);
        return generoRepository.findById(id)
            .map(generoMapper::toDto);
    }

    /**
     * Delete the genero by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Genero : {}", id);
        generoRepository.deleteById(id);
    }
}
