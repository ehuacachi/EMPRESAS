package com.okcompu.ecommerce.backendokcompu.service;

import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacenPK;

import java.util.List;

public interface ProductoAlmacenService extends CRUD<ProductoAlmacen, ProductoAlmacenPK> {

    ProductoAlmacen findByProductoAndAlmacen(Long idProducto, Long idAlmacen);
    List<ProductoAlmacen> findByProducto(Long idProducto);
    List<ProductoAlmacen> findByAlmacen(Long idAlmacen);

}
