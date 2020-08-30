package com.norfork.service.mapper;

import com.norfork.domain.*;
import com.norfork.service.dto.TallaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Talla} and its DTO {@link TallaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TallaMapper extends EntityMapper<TallaDTO, Talla> {



    default Talla fromId(Long id) {
        if (id == null) {
            return null;
        }
        Talla talla = new Talla();
        talla.setId(id);
        return talla;
    }
}
