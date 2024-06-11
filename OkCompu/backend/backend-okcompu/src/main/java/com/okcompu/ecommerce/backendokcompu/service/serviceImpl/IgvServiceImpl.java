package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Igv;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.IgvRepository;
import com.okcompu.ecommerce.backendokcompu.service.IgvService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IgvServiceImpl extends CRUDImpl<Igv, Long> implements IgvService {

    private final IgvRepository repo;

    @Override
    protected GenericRepo<Igv, Long> getRepo() {
        return repo;
    }
}
