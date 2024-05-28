package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name = "proveedores")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Proveedor extends Base{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idProveedor;

    @ManyToOne
    @JoinColumn(name = "id_tipodocumento", nullable = false, foreignKey = @ForeignKey(name = "KF_PROVEEDOR_TIPODOCUMENTO"))
    private TipoDocumento tipoDocumento;

    private String nroDocumento;

    private String razonSocial;

    private Byte estado;

    @PrePersist
    public void prePersist() {
        this.estado = 1;
    }

}
