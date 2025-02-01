package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@Table(name = "empleados")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true) //, callSuper = true
public class Empleado { //extends Base
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idEmpleado;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @Column(name = "apellido_paterno", nullable = false, length = 50)
    @NotBlank(message = "El apellido paterno es obligatorio")
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 50)
    private String apellidoMaterno;

    @ManyToOne
    @JoinColumn(name = "id_tipodocumento", nullable = false, foreignKey = @ForeignKey(name = "FK_EMPLEADO_TIPODOCUMENTO"))
    private TipoDocumento tipoDocumento;

    @Column(name = "nro_documento")
    private String nroDocumento;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

//    @Enumerated(EnumType.STRING)
//    private UserType userType;

    private String foto;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEmpleado estado;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "empleado_rol",
            joinColumns = @JoinColumn(name = "id_empleado", referencedColumnName = "idEmpleado"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol")
    )
    private List<Rol> roles;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefono> telefonos;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Domicilio> domicilios;

}