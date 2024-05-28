package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@Embeddable
@EqualsAndHashCode
public class ProductoAlmacenPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    private Almacen almacen;

}
