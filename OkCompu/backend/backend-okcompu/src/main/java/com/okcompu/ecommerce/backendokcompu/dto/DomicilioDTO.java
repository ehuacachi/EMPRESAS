package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Cliente;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.model.Proveedor;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DomicilioDTO {

    @EqualsAndHashCode.Include
    private Long idDomicilio;

    private String direccion;
    private String numero;
    private String manzana;
    private String lote;
    private String referencia;
    //Ubigeo

    @JsonBackReference("rf_empleado_domicilio")
    private EmpleadoDTO empleado;

    @JsonBackReference("rf_cliente_domicilio")
    private ClienteDTO cliente;

    //private ProveedorDTO proveedor;

    @JsonBackReference("rf_almacen_domicilio")
    private AlmacenDTO almacen;
}
