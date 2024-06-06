package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.repo.EmpleadoRepository;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl extends CRUDImpl<Empleado, Long> implements EmpleadoService {

    private final EmpleadoRepository repo;
    @Override
    protected GenericRepo<Empleado, Long> getRepo() {
        return repo;
    }
}
