package com.okcompu.ecommerce.backendokcompu.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IgvDTO {
    @EqualsAndHashCode.Include
    private Long idIgv;
    @NotNull
    @Size(min = 3, max = 20)
    private String actividad;
    @NotNull
    @DecimalMax("3")
    private Double valor;
    @NotNull
    private LocalDateTime igvFecha;
    private Byte estado;
}
