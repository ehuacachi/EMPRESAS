package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.model.Venta;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DetalleVentaDTO {

    @EqualsAndHashCode.Include
    private Long idDetalleVenta;

    private ProductoDTO producto;

    private Integer cantidad;

    private Double precio;

    private Double descuento;

    private Double subTotal;

    @NotNull
    @JsonBackReference
    private VentaDTO venta;
}
