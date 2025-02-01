package com.okcompu.ecommerce.backendokcompu.service;

import com.okcompu.ecommerce.backendokcompu.model.Rol;

import java.util.List;

public interface RolService extends CRUD<Rol, Integer>{
    // Métodos adicionales
    List<Rol> listarActivos();
    Rol buscarPorNombre(String nombre);
}