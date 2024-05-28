package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idDetalle;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLECOMPRA_PRODUCTO"))
    private Producto producto;

    private Integer cantidad;

    private Double precio;

    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "id_compra", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLECOMPRA_COMPRA"))
    private Compra compra;

}

