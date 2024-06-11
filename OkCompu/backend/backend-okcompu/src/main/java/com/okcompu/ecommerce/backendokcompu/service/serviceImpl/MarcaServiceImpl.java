package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Marca;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.MarcaRepository;
import com.okcompu.ecommerce.backendokcompu.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl extends CRUDImpl<Marca, Long> implements MarcaService {

    private final MarcaRepository repo;

    @Override
    protected GenericRepo<Marca, Long> getRepo() {
        return repo;
    }
}
