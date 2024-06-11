package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.UnidadMedida;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.UnidadMedidaRepository;
import com.okcompu.ecommerce.backendokcompu.service.UnidadMedidaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnidadMedidaServiceImpl extends CRUDImpl<UnidadMedida, Long> implements UnidadMedidaService {

    private final UnidadMedidaRepository repo;

    @Override
    protected GenericRepo<UnidadMedida, Long> getRepo() {
        return repo;
    }
}
