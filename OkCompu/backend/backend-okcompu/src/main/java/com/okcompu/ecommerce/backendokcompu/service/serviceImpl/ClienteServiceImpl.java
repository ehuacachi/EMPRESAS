package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.Cliente;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.ClienteRepository;
import com.okcompu.ecommerce.backendokcompu.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl extends CRUDImpl<Cliente, Long> implements ClienteService {

    private final ClienteRepository repo;

    @Override
    protected GenericRepo<Cliente, Long> getRepo() {
        return repo;
    }
}
