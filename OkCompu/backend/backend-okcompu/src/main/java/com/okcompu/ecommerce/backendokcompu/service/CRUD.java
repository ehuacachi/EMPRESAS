package com.okcompu.ecommerce.backendokcompu.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CRUD<T, ID> {

    List<T> findAll();

    Page<T> findAll(Pageable pageable);

    T findById(ID id);

    T create(T t);

    T update(ID id, T t);

    void delete(ID id);

}
