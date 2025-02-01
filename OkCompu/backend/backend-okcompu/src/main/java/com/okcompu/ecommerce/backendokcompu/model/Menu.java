package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "menus")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long idMenu;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El ícono es obligatorio")
    private String icono;

    @Column(nullable = false, length = 20)
    @NotBlank(message = "El nombre del menú es obligatorio")
    private String nombre;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "La URL es obligatoria")
    private String url;

    @Column(name = "activo")
    private Boolean activo = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "menu_rol",
            joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"),
            inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol")
    )
    private List<Rol> roles;
}
