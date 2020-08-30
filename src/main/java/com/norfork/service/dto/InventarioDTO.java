package com.norfork.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link com.norfork.domain.Inventario} entity.
 */
public class InventarioDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal stock;


    private Long productoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InventarioDTO inventarioDTO = (InventarioDTO) o;
        if (inventarioDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inventarioDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InventarioDTO{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            ", producto=" + getProductoId() +
            "}";
    }
}
