package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Comprobante;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.ComprobanteRepository;
import com.okcompu.ecommerce.backendokcompu.service.ComprobanteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComprobanteServiceImpl extends CRUDImpl<Comprobante, Long> implements ComprobanteService {

    private final ComprobanteRepository repo;

    @Override
    protected GenericRepo<Comprobante, Long> getRepo() {
        return repo;
    }
}
