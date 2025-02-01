package com.okcompu.ecommerce.backendokcompu.service;

import com.okcompu.ecommerce.backendokcompu.model.Menu;

import java.util.List;

public interface MenuService extends CRUD<Menu, Long> {
    // Métodos adicionales
    List<Menu> listarActivos();
    List<Menu> buscarPorNombre(String nombre);
    List<Menu> obtenerMenusPorRol(Integer idRol);
}
