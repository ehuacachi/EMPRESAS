package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.model.TipoDocumento;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.TipoDocumentoRepository;
import com.okcompu.ecommerce.backendokcompu.service.TipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TipoDocumentoServiceImpl extends CRUDImpl<TipoDocumento, Long> implements TipoDocumentoService {

    private final TipoDocumentoRepository repo;

    @Override
    protected GenericRepo<TipoDocumento, Long> getRepo() {
        return repo;
    }
}
