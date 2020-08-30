package com.norfork.service.mapper;

import com.norfork.domain.*;
import com.norfork.service.dto.InventarioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Inventario} and its DTO {@link InventarioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProductoMapper.class})
public interface InventarioMapper extends EntityMapper<InventarioDTO, Inventario> {

    @Mapping(source = "producto.id", target = "productoId")
    InventarioDTO toDto(Inventario inventario);

    @Mapping(source = "productoId", target = "producto")
    Inventario toEntity(InventarioDTO inventarioDTO);

    default Inventario fromId(Long id) {
        if (id == null) {
            return null;
        }
        Inventario inventario = new Inventario();
        inventario.setId(id);
        return inventario;
    }
}
