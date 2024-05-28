package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.service.CRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements CRUD<T, ID> {
    //Se neceista un objeto que se comporte de diferentes formas
    // segun quien lo invoque: POLIMORFISMO, sin embargo esta declaracion no ayuda
    //private final GenerationType<T, ID> repo;

    //Entoces creamos un metodo ABSTRACTO
    // protectec: para que este alcance del paquete y las herencias.
    // Como es un metodo abastracto la clase tambien debe ser abstracto.
    protected abstract GenericRepo<T, ID> getRepo();

    @Override
    public List<T> findAll() {
        return getRepo().findAll();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return getRepo().findAll(pageable);
    }

    @Override
    public T findById(ID id) {
        //pendiente por revisar el null
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public T create(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        //Validad ID
        return getRepo().save(t);
    }

    @Override
    public void delete(ID id) {
        getRepo().deleteById(id);
    }
}
