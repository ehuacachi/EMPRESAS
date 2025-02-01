package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;


import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.model.Menu;
import com.okcompu.ecommerce.backendokcompu.model.Rol;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.RolRepository;
import com.okcompu.ecommerce.backendokcompu.service.RolService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolServiceImpl extends CRUDImpl<Rol, Integer> implements RolService {
    private final RolRepository repo;

    @Override
    protected GenericRepo<Rol, Integer> getRepo() {
        return repo;
    }

    @Override
    public void delete(Integer id) {
        Rol rol = getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        rol.setActivo(false);
        getRepo().save(rol);
    }

    @Override
    public List<Rol> listarActivos() {
        return repo.findByActivo(true);
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        return repo.findByNombre(nombre)
                .orElseThrow(() -> new ModelNotFoundException("Rol no encontrado"));
    }
}
