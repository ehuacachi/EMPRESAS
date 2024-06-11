package com.okcompu.ecommerce.backendokcompu.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
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
    private String nombreCompleto;

    @NotNull
    private String apPaterno;

    @NotNull
    private String apMaterno;

    @NotNull
    private Long idTipoDocumento;

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

    private Byte estado;

    //private List<Rol> roles;

    @NotNull
    @JsonManagedReference("rf_empleado_telefono")
    private List<TelefonoDTO> telefonos;

    @NotNull
    @JsonManagedReference("rf_empleado_domicilio")
    private List<DomicilioDTO> domicilios;

}
