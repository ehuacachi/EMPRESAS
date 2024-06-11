package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Venta;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.VentaRepository;
import com.okcompu.ecommerce.backendokcompu.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends CRUDImpl<Venta, Long> implements VentaService {

    private final VentaRepository repo;

    @Override
    protected GenericRepo<Venta, Long> getRepo() {
        return repo;
    }
}
