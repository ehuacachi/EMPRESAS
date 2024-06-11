package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.AlmacenRepository;
import com.okcompu.ecommerce.backendokcompu.service.AlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlmacenServiceImpl extends CRUDImpl<Almacen, Long> implements AlmacenService {

    private final AlmacenRepository repo;

    @Override
    protected GenericRepo<Almacen, Long> getRepo() {
        return repo;
    }
}
