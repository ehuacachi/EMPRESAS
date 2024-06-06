package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.ProductoRepository;
import com.okcompu.ecommerce.backendokcompu.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl extends CRUDImpl<Producto, Long> implements ProductoService {

    private final ProductoRepository repo;

    @Override
    protected GenericRepo<Producto, Long> getRepo() {
        return repo;
    }
}
