package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okcompu.ecommerce.backendokcompu.model.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VentaDTO {

    @EqualsAndHashCode.Include
    private Long idVenta;

    private LocalDateTime fecha;

    private Double total;

    private Empleado empleado;

    private AlmacenDTO almacen;

    private ClienteDTO cliente;

    private ComprobanteDTO comprobante;

    private String serie;

    private String numero;

    private Moneda moneda;

    private FormaPagoDTO formaPago;

    private IgvDTO igv;

    @JsonManagedReference
    private List<DetalleVentaDTO> detalleVentas;
}
