package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Moneda;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.MonedaRepository;
import com.okcompu.ecommerce.backendokcompu.service.MonedaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonedaServiceImpl extends CRUDImpl<Moneda, Long> implements MonedaService {

    private final MonedaRepository repo;

    @Override
    protected GenericRepo<Moneda, Long> getRepo() {
        return repo;
    }
}
