package com.okcompu.ecommerce.backendokcompu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProductoDTO {

    @EqualsAndHashCode.Include
    private Long idProducto;

    private String descripcion;

    private String modelo;

    private String code;

    private String urlImage;

    private Double precioCompra = 0.00;

    private Double precioVenta = 0.00;

    private Double utilidad = 0.00;

    private Double margenUtilidad = 0.00;

    private Byte estado;

    private MarcaDTO marca;

    private UnidadMedidaDTO unidadMedida;

    private CategoriaDTO categoria;
}
