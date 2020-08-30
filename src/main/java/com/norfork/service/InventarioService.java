package com.norfork.service;

import com.norfork.domain.Inventario;
import com.norfork.repository.InventarioRepository;
import com.norfork.service.dto.InventarioDTO;
import com.norfork.service.mapper.InventarioMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Inventario}.
 */
@Service
@Transactional
public class InventarioService {

    private final Logger log = LoggerFactory.getLogger(InventarioService.class);

    private final InventarioRepository inventarioRepository;

    private final InventarioMapper inventarioMapper;

    public InventarioService(InventarioRepository inventarioRepository, InventarioMapper inventarioMapper) {
        this.inventarioRepository = inventarioRepository;
        this.inventarioMapper = inventarioMapper;
    }

    /**
     * Save a inventario.
     *
     * @param inventarioDTO the entity to save.
     * @return the persisted entity.
     */
    public InventarioDTO save(InventarioDTO inventarioDTO) {
        log.debug("Request to save Inventario : {}", inventarioDTO);
        Inventario inventario = inventarioMapper.toEntity(inventarioDTO);
        inventario = inventarioRepository.save(inventario);
        return inventarioMapper.toDto(inventario);
    }

    /**
     * Get all the inventarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<InventarioDTO> findAll() {
        log.debug("Request to get all Inventarios");
        return inventarioRepository.findAll().stream()
            .map(inventarioMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one inventario by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InventarioDTO> findOne(Long id) {
        log.debug("Request to get Inventario : {}", id);
        return inventarioRepository.findById(id)
            .map(inventarioMapper::toDto);
    }

    /**
     * Delete the inventario by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Inventario : {}", id);
        inventarioRepository.deleteById(id);
    }
}
