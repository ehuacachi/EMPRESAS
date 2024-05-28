package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "domicilios")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Domicilio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idDomicilio;
    private String direccion;
    private String numero;
    private String manzana;
    private String lote;
    private String referencia;
    //Ubigeo

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = true, foreignKey = @ForeignKey(name = "FK_DOMICILIO_EMPLEADO"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = true, foreignKey = @ForeignKey(name = "FK_DOMICILIO_CLIENTE"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = true, foreignKey = @ForeignKey(name = "FK_DOMICILIO_PROVEEDOR"))
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_almacen", nullable = true, foreignKey = @ForeignKey(name = "FK_DOMICILIO_ALMACEN"))
    private Almacen almacen;

}
