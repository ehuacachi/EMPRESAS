package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //, callSuper = true
public class Venta { //extends Base

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idVenta;

    private LocalDateTime fecha;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_EMPLEADO"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_almacen", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_ALMACEN"))
    private Almacen almacen;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_CLIENTE"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_comprobante", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_COMPROBANTE"))
    private Comprobante comprobante;

    private String serie;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_moneda", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_MONEDA"))
    private Moneda moneda;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_FORMA_PAGO"))
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "id_igv", nullable = false, foreignKey = @ForeignKey(name = "FK_VENTA_IGV"))
    private Igv igv;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalleVentas;

    private String estado;
}