package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacenPK;
import com.okcompu.ecommerce.backendokcompu.repo.AlmacenRepository;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.ProductoAlmacenRepository;
import com.okcompu.ecommerce.backendokcompu.repo.ProductoRepository;
import com.okcompu.ecommerce.backendokcompu.service.ProductoAlmacenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoAlmacenServiceImpl extends CRUDImpl<ProductoAlmacen, ProductoAlmacenPK> implements ProductoAlmacenService {

    private final ProductoRepository productoRepository;
    private final AlmacenRepository almacenRepository;
    private final ProductoAlmacenRepository repo;

    @Override
    protected GenericRepo<ProductoAlmacen, ProductoAlmacenPK> getRepo() {
        return repo;
    }

    @Override
    public ProductoAlmacen findByProductoAndAlmacen(Long idProducto, Long idAlmacen) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + idProducto));
        Almacen almacen = almacenRepository.findById(idAlmacen).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + idAlmacen));
        return repo.findByProductoAndAlmacen(producto, almacen);
    }

    @Override
    public List<ProductoAlmacen> findByProducto(Long idProducto) {
        Producto producto = productoRepository.findById(idProducto).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + idProducto));
        return repo.findByProducto(producto);
    }

    @Override
    public List<ProductoAlmacen> findByAlmacen(Long idAlmacen) {
        Almacen almacen = almacenRepository.findById(idAlmacen).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + idAlmacen));
        return repo.findByAlmacen(almacen);
    }
}
