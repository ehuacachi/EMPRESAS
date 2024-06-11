package com.okcompu.ecommerce.backendokcompu.dto;

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
public class UnidadMedidaDTO {

    @EqualsAndHashCode.Include
    private Long idUnidadMedida;
    @NotNull
    private String descripcion;
    @NotNull
    @Size(max = 5)
    private String abreviatura;

    private Byte estado;
}
