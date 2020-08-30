package com.norfork.service.mapper;

import com.norfork.domain.*;
import com.norfork.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {GeneroMapper.class, TallaMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "genero.id", target = "generoId")
    @Mapping(source = "talla.id", target = "tallaId")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "generoId", target = "genero")
    @Mapping(source = "tallaId", target = "talla")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
