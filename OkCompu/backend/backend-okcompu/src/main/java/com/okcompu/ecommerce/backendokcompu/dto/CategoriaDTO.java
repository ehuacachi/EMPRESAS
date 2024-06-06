package com.okcompu.ecommerce.backendokcompu.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
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
public class CategoriaDTO {

    @EqualsAndHashCode.Include
    private Long idCategoria;

    @NotNull
    @NotEmpty
    @Size(min = 5, max = 30, message = "{descripcion.size}")
    private String descripcion;

    private Byte estado;
}
