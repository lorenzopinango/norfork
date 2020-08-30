package com.norfork.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A Inventario.
 */
@Entity
@Table(name = "inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence_inventario")
    @SequenceGenerator(name = "sequence_inventario", sequenceName = "sequence_inventario",
        initialValue = 1, allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "stock", precision = 21, scale = 2, nullable = false)
    private BigDecimal stock;

    @ManyToOne(optional = false)
    @NotNull
    private Producto producto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public Inventario stock(BigDecimal stock) {
        this.stock = stock;
        return this;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public Producto getProducto() {
        return producto;
    }

    public Inventario producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Inventario)) {
            return false;
        }
        return id != null && id.equals(((Inventario) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Inventario{" +
            "id=" + getId() +
            ", stock=" + getStock() +
            "}";
    }
}
