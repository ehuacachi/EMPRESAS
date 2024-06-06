package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.annotation.PreDestroy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categorias")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idCategoria;

    @Column(unique = true)
    private String descripcion;

    private Byte estado;
    @PrePersist
    public void prePersist() {
        this.estado = 1;
    }

    @PreUpdate
    public void preUpdate() {
        this.estado = 1;
    }


}
