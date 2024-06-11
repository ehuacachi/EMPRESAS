package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.DetalleVenta;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.DetalleVentaRepository;
import com.okcompu.ecommerce.backendokcompu.service.DetalleVentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetalleVentaServiceImpl extends CRUDImpl<DetalleVenta, Long> implements DetalleVentaService {

    private final DetalleVentaRepository repo;

    @Override
    protected GenericRepo<DetalleVenta, Long> getRepo() {
        return repo;
    }
}
