package com.okcompu.ecommerce.backendokcompu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TipoDocumentoDTO {

    @EqualsAndHashCode.Include
    private Long idTipoDocumento;

    @NotNull
    private String descripcion;

    @NotNull
    @Size(max = 5)
    private String abreviatura;
}
