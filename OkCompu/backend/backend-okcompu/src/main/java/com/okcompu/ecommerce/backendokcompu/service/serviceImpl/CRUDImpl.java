package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.service.CRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements CRUD<T, ID> {
    //Se neceista un objeto que se comporte de diferentes formas
    // segun quien lo invoque: POLIMORFISMO, sin embargo esta declaracion no ayuda
    //private final GenericRepo<T, ID> repo;

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
        // supllier: Es una expresion lamba sin parametros de ingreso, pero devuelve algo.
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public T create(T t) {
        return getRepo().save(t);
    }

    @Override
    public T update(ID id, T t) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        return getRepo().save(t);
    }

    @Override
    public void delete(ID id) {
        getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        getRepo().deleteById(id);
    }
}
