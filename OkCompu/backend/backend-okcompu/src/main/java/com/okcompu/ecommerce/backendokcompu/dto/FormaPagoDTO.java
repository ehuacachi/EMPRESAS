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
public class FormaPagoDTO {

    @EqualsAndHashCode.Include
    private Long idFormaPago;
    @NotNull
    @Size(min = 3, max = 15)
    private String nombre;

    private Byte estado;

}
