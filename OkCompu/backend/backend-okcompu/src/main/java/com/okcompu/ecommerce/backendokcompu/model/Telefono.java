package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefonos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Telefono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idTelefono;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true, foreignKey = @ForeignKey(name = "FK_TELEFONO_EMPLEADO"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = true, foreignKey = @ForeignKey(name = "FK_TELEFONO_CLIENTE"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = true, foreignKey = @ForeignKey(name = "FK_TELEFONO_PROVEEDOR"))
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_almacen", nullable = true, foreignKey = @ForeignKey(name = "FK_TELEFONO_ALMACEN"))
    private Almacen almacen;
}
