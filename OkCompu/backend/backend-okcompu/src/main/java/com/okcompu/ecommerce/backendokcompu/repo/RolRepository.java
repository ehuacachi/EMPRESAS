package com.okcompu.ecommerce.backendokcompu.repo;

import com.okcompu.ecommerce.backendokcompu.model.Rol;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolRepository extends GenericRepo<Rol, Integer> {
    List<Rol> findByActivo(Boolean activo);
    Optional<Rol> findByNombre(String nombre);
}
