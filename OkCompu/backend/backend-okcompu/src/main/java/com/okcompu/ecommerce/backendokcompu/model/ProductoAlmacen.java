package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ProductoAlmacenPK.class)
public class ProductoAlmacen {
    @Id
    private Producto producto;

    @Id
    private Almacen almacen;

    private Integer cantidad;
}
