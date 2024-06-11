package com.okcompu.ecommerce.backendokcompu.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class AlmacenDTO {

    @EqualsAndHashCode.Include
    private Long idAlmacen;

    private String descripcion;

    @NotNull
    @JsonManagedReference("rf_almacen_telefono")
    private List<TelefonoDTO> telefonos;

    @NotNull
    @JsonManagedReference("rf_almacen_domicilio")
    private List<DomicilioDTO> domicilios;
}
