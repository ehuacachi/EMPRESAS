package com.okcompu.ecommerce.backendokcompu.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MarcaDTO {

    @EqualsAndHashCode.Include
    private Long idMarca;

    @NotNull
    private String descripcion;

    private String foto;

    private Byte estado;
}
