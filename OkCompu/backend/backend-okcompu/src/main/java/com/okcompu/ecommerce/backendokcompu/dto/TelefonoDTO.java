package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Cliente;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.model.Proveedor;
import jakarta.persistence.Column;
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
public class TelefonoDTO {

    @EqualsAndHashCode.Include
    private Long idTelefono;

    private String numero;

    @JsonBackReference("rf_empleado_telefono")
    private EmpleadoDTO empleado;

    @JsonBackReference("rf_cliente_telefono")
    private ClienteDTO cliente;

    //private ProveedorDTO proveedor;

    @JsonBackReference("rf_almacen_telefono")
    private AlmacenDTO almacen;
}
