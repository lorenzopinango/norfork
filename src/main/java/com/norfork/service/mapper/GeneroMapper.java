package com.norfork.service.mapper;

import com.norfork.domain.*;
import com.norfork.service.dto.GeneroDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Genero} and its DTO {@link GeneroDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GeneroMapper extends EntityMapper<GeneroDTO, Genero> {



    default Genero fromId(Long id) {
        if (id == null) {
            return null;
        }
        Genero genero = new Genero();
        genero.setId(id);
        return genero;
    }
}
