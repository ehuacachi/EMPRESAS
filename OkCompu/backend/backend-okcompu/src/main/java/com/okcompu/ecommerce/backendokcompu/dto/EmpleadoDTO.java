package com.okcompu.ecommerce.backendokcompu.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.okcompu.ecommerce.backendokcompu.model.Rol;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmpleadoDTO {

    @EqualsAndHashCode.Include
    private Long idEmpleado;

    @NotNull
    @NotBlank(message = "Los nombres son obligatorios")
    @Column(nullable = false, length = 50)
    private String nombreCompleto;

    @NotNull
    @NotBlank(message = "El apellido paterno es obligatorio")
    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apPaterno;

    @NotNull
    @Column(name = "apellido_materno", length = 50)
    private String apMaterno;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    @NotNull
    private String nroDocumento;

    @NotNull
    @Size(max = 10)
    private String usuario;
    @NotNull
    private String clave;

    @Email
    private String correo;

    //private UserType userType;

    private String avatar;

    private String estado;

    private List<Rol> roles;

    @NotNull
    @JsonManagedReference("rf_empleado_telefono")
    private List<TelefonoDTO> telefonos;

    @NotNull
    @JsonManagedReference("rf_empleado_domicilio")
    private List<DomicilioDTO> domicilios;

}
