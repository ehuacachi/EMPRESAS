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
import jakarta.transaction.Transactional;
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
    public ProductoAlmacen create(ProductoAlmacen productoAlmacen) {
        // Validar que existan producto y almacén
        Producto producto = productoRepository.findById(productoAlmacen.getProducto().getIdProducto())
                .orElseThrow(() ->
                        new ModelNotFoundException("Producto no encontrado"));
        Almacen almacen = almacenRepository.findById(productoAlmacen.getAlmacen().getIdAlmacen())
                .orElseThrow(() -> new ModelNotFoundException("Almacén no encontrado"));

        // Validar que no exista la relación
        if (repo.existsByProductoAndAlmacen(producto, almacen)) {
            throw new IllegalArgumentException("Ya existe stock para este producto en el almacén");
        }

        productoAlmacen.setProducto(producto);
        productoAlmacen.setAlmacen(almacen);
        return repo.save(productoAlmacen);
    }

    @Override
    public ProductoAlmacen update(ProductoAlmacenPK id, ProductoAlmacen productoAlmacen) {
        // Validar que exista la relación
        ProductoAlmacen existente = repo.findById(id)
                .orElseThrow(() -> new ModelNotFoundException("Relación Producto-Almacén no encontrada"));

        // Actualizar solo la cantidad
        existente.setCantidad(productoAlmacen.getCantidad());
        return repo.save(existente);
    }

    @Override
    public ProductoAlmacen updateStock(Long idProducto, Long idAlmacen, Integer cantidad) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ModelNotFoundException("Producto no encontrado"));
        Almacen almacen = almacenRepository.findById(idAlmacen)
                .orElseThrow(() -> new ModelNotFoundException("Almacén no encontrado"));

        ProductoAlmacen productoAlmacen = repo.findByProductoAndAlmacen(producto, almacen);
        if (productoAlmacen == null) {
            productoAlmacen = new ProductoAlmacen();
            productoAlmacen.setProducto(producto);
            productoAlmacen.setAlmacen(almacen);
        }
        productoAlmacen.setCantidad(cantidad);
        return repo.save(productoAlmacen);
    }

    @Transactional
    @Override
    public void deleteByProductoAndAlmacen(Long idProducto, Long idAlmacen) {
        Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new ModelNotFoundException("Producto no encontrado"));
        Almacen almacen = almacenRepository.findById(idAlmacen)
                .orElseThrow(() -> new ModelNotFoundException("Almacén no encontrado"));

        repo.deleteByProductoAndAlmacen(producto, almacen);
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
