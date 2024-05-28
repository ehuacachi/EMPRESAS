package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Producto extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idProducto;

    private String descripcion;

    private String code;

    private String urlImage;

    private Double precioCompra = 0.00;

    private Double precioVenta = 0.00;

    private Double utilidad = 0.00;

    private Double margenUtilidad = 0.00;

    private Byte estado;

    @ManyToOne
    @JoinColumn(name = "id_marca", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCTO_MARCA"))
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "id_unidad_medida", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCTO_UNIDAD_MEDIDA"))
    private UnidadMedida unidadMedida;

    @ManyToOne
    @JoinColumn(name = "id_categoria", nullable = false, foreignKey = @ForeignKey(name = "FK_PRODUCTO_CATEGORIA"))
    private Categoria categoria;

    @PrePersist
    public void prePersist() {
        this.estado = 1;
    }
}
