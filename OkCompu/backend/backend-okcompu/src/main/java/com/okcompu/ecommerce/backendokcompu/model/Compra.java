package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "compras")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Compra extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idCompra;

    private LocalDateTime fecha;

    private Double total;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_EMPLEADO"))
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "id_proveedor", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_PROVEEDOR"))
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "id_comprobante", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_COMPROBANTE"))
    private Comprobante comprobante;

    private String serie;

    private String numero;

    @ManyToOne
    @JoinColumn(name = "id_moneda", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_MONEDA"))
    private Moneda moneda;

    @ManyToOne
    @JoinColumn(name = "id_forma_pago", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_FORMA_PAGO"))
    private FormaPago formaPago;

    @ManyToOne
    @JoinColumn(name = "id_igv", nullable = false, foreignKey = @ForeignKey(name = "FK_COMPRA_IGV"))
    private Igv igv;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCompra> detalleCompras;


}