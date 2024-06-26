package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name="clientes")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)  //, callSuper = true
public class Cliente  { //extends Base

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idCliente;

    @Column(nullable = false)
    private String nombres;

    private String apellidos;

    @ManyToOne
    @JoinColumn(name = "id_tipodocumento", nullable = false, foreignKey = @ForeignKey(name = "FK_CLIENTE_TIPODOCUMENTO"))
    private TipoDocumento tipoDocumento;

    private String nroDocumento;

    @Column(unique = true)
    private String email;

    private String foto;

    private Byte estado;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicilio> domicilios;
}