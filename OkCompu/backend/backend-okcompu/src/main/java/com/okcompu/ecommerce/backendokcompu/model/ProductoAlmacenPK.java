package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Embeddable
@EqualsAndHashCode
@Getter
@Setter
public class ProductoAlmacenPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_almacen")
    private Almacen almacen;

}
