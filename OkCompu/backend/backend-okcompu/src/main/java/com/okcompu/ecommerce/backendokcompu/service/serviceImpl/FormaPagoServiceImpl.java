package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.FormaPago;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.FormaPagoRepository;
import com.okcompu.ecommerce.backendokcompu.service.FormaPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormaPagoServiceImpl extends CRUDImpl<FormaPago, Long> implements FormaPagoService {

    private final FormaPagoRepository repo;

    @Override
    protected GenericRepo<FormaPago, Long> getRepo() {
        return repo;
    }
}
