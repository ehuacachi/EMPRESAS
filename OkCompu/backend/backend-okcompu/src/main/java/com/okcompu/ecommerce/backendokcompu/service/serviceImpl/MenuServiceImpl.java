package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.model.Menu;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.MarcaRepository;
import com.okcompu.ecommerce.backendokcompu.repo.MenuRepository;
import com.okcompu.ecommerce.backendokcompu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl extends CRUDImpl<Menu, Long> implements MenuService {

    private final MenuRepository repo;

    @Override
    protected GenericRepo<Menu, Long> getRepo() {
        return this.repo;
    }

    @Override
    public void delete(Long id) {
        Menu menu = getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        menu.setActivo(false);
        getRepo().save(menu);
    }

    @Override
    public List<Menu> listarActivos() {
        return repo.findByActivo(true);
    }

    @Override
    public List<Menu> buscarPorNombre(String nombre) {
        return repo.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Menu> obtenerMenusPorRol(Integer idRol) {
        // Implementación dependerá de tu estructura de base de datos
        // Podrías necesitar un método personalizado en el repositorio
        return null; // Implementación pendiente
    }

}
