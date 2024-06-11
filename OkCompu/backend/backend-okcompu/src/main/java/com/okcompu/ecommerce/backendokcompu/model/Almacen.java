package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "almacenes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Almacen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idAlmacen;
    private String descripcion;

    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos;

    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicilio> domicilios;


}
