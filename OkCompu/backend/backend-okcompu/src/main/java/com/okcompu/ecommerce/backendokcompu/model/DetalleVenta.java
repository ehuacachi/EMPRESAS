package com.okcompu.ecommerce.backendokcompu.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idDetalleVenta;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLEVENTA_PRODUCTO"))
    private Producto producto;

    private Integer cantidad;

    private Double precio;

    private Double descuento;

    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "id_venta", nullable = false, foreignKey = @ForeignKey(name = "FK_DETALLEVENTA_VENTA"))
    private Venta venta;

}

