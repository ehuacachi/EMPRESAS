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
public class MonedaDTO {

    @EqualsAndHashCode.Include
    private Long idMoneda;
    @NotNull
    private String descripcion;
    @NotNull
    private String abreviatura;

    private Byte estado;

    @PrePersist
    public void prePersist() {
        this.estado = 1;
    }
}
