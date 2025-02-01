package com.okcompu.ecommerce.backendokcompu.repo;

import com.okcompu.ecommerce.backendokcompu.model.Menu;

import java.util.List;

public interface MenuRepository extends GenericRepo<Menu, Long>{
    // Métodos personalizados si los necesitas
    List<Menu> findByActivo(Boolean activo);
    List<Menu> findByNombreContainingIgnoreCase(String nombre);
}
