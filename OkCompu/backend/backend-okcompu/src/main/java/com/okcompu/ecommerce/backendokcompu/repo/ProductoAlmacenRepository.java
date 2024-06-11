package com.okcompu.ecommerce.backendokcompu.repo;

import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacenPK;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ProductoAlmacenRepository extends GenericRepo<ProductoAlmacen, ProductoAlmacenPK> {


    ProductoAlmacen findByProductoAndAlmacen(Producto producto, Almacen almacen);
    List<ProductoAlmacen> findByProducto(Producto producto);
    List<ProductoAlmacen> findByAlmacen(Almacen almacen);



}
