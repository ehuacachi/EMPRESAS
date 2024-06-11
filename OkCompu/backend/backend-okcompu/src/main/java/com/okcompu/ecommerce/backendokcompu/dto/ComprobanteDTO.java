package com.okcompu.ecommerce.backendokcompu.dto;

import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ComprobanteDTO {

    @EqualsAndHashCode.Include
    private Long idComprobante;

    private String descripcion;

    private String abreviatura;

}
