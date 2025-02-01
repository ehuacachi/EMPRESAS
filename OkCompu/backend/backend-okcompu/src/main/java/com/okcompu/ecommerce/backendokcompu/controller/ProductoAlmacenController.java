package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.AlmacenDTO;
import com.okcompu.ecommerce.backendokcompu.dto.ProductoAlmacenDTO;
import com.okcompu.ecommerce.backendokcompu.dto.ProductoDTO;
import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacenPK;
import com.okcompu.ecommerce.backendokcompu.service.ProductoAlmacenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/productoAlmacens")
@RequiredArgsConstructor
public class ProductoAlmacenController {

    private final ProductoAlmacenService productoAlmacenService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductoAlmacenDTO>> findAll() {
        List<ProductoAlmacenDTO> dtos = productoAlmacenService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ProductoAlmacenDTO>> findAllPage(@PathVariable Integer page) {
        Page<ProductoAlmacenDTO> dtos = productoAlmacenService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/codProducto/{idProducto}")
    public ResponseEntity<List<ProductoAlmacenDTO>> findByIdProducto(@PathVariable("idProducto") Long idProducto) {
        List<ProductoAlmacen> objs = productoAlmacenService.findByProducto(idProducto);
        List<ProductoAlmacenDTO> objDTO = mapper.map(objs, new TypeToken<List<ProductoAlmacenDTO>>(){}.getType());
        return ResponseEntity.ok(objDTO);
    }

    @GetMapping("/codAlmacen/{idAlmacen}")
    public ResponseEntity<List<ProductoAlmacenDTO>> findByIdAlmacen(@PathVariable("idAlmacen") Long idAlmacen) {
        List<ProductoAlmacen> objs = productoAlmacenService.findByAlmacen(idAlmacen);
        List<ProductoAlmacenDTO> objDTO = mapper.map(objs, new TypeToken<List<ProductoAlmacenDTO>>(){}.getType());
        return ResponseEntity.ok(objDTO);
    }

    @GetMapping("/stock/producto/{idProducto}/almacen/{idAlmacen}")
    public ResponseEntity<ProductoAlmacenDTO> findByProductoAndAlmacen(@PathVariable Long idProducto, @PathVariable Long idAlmacen) {

        ProductoAlmacen obj = productoAlmacenService.findByProductoAndAlmacen(idProducto, idAlmacen);

        return ResponseEntity.ok(converDTO(obj));

    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductoAlmacenDTO dto) {
        ProductoAlmacen obj = productoAlmacenService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj).toUri();
        return  ResponseEntity.created(location).build();

    }

    @PutMapping("/producto/{idProducto}/almacen/{idAlmacen}")
    public ResponseEntity<ProductoAlmacenDTO> update(
            @PathVariable Long idProducto,
            @PathVariable Long idAlmacen,
            @Valid @RequestBody ProductoAlmacenDTO dto) {

        ProductoAlmacenPK id = new ProductoAlmacenPK();
        id.setProducto(new Producto());
        id.getProducto().setIdProducto(idProducto);
        id.setAlmacen(new Almacen());
        id.getAlmacen().setIdAlmacen(idAlmacen);

        ProductoAlmacen obj = productoAlmacenService.update(id, convertEntity(dto));
        return ResponseEntity.ok(converDTO(obj));
    }

    @PutMapping("/stock/producto/{idProducto}/almacen/{idAlmacen}")
    public ResponseEntity<ProductoAlmacenDTO> updateStock(
            @PathVariable Long idProducto,
            @PathVariable Long idAlmacen,
            @RequestParam Integer cantidad) {

        ProductoAlmacen obj = productoAlmacenService.updateStock(idProducto, idAlmacen, cantidad);
        return ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("/producto/{idProducto}/almacen/{idAlmacen}")
    public ResponseEntity<Void> delete(@PathVariable Long idProducto, @PathVariable Long idAlmacen) {
        productoAlmacenService.deleteByProductoAndAlmacen(idProducto, idAlmacen);
        return ResponseEntity.noContent().build();
    }

    private ProductoAlmacen convertEntity(ProductoAlmacenDTO dto) {
        return mapper.map(dto, ProductoAlmacen.class);
    }

    private ProductoAlmacenDTO converDTO(ProductoAlmacen productoAlmacen) {
        return mapper.map(productoAlmacen, ProductoAlmacenDTO.class);
    }
}
