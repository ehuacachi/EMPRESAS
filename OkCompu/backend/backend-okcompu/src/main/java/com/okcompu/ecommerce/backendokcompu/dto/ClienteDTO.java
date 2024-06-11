package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClienteDTO {

    @EqualsAndHashCode.Include
    private Long idCliente;

    @NotNull
    private String nombres;

    @NotNull
    private String apellidos;

    @NotNull
    private TipoDocumentoDTO tipoDocumento;

    private String nroDocumento;

    @Email
    private String email;

    private String foto;

    private Byte estado;

    @JsonManagedReference("rf_cliente_telefono")
    private List<TelefonoDTO> telefonos;

    @JsonManagedReference("rf_cliente_domicilio")
    private List<DomicilioDTO> domicilios;

}
