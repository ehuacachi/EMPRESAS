package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRol;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "El nombre del rol es obligatorio")
    private String nombre;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "La descripción del rol es obligatoria")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo = true;
}
