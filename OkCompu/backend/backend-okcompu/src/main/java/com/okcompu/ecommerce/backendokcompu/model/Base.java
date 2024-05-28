package com.okcompu.ecommerce.backendokcompu.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
public class Base {

    private String usuarioCreacion;
    private Date fechaCreacion;
    private String usuarioModiciacion;
    private Date fechaModiciacion;

    @PrePersist
    public void prePresist() {
        fechaCreacion = new Date();

    }
    @PreUpdate
    public void preUpdate() {
        fechaModiciacion = new Date();

    }
}
