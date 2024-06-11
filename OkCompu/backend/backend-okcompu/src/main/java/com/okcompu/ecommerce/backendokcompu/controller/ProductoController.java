package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.ProductoDTO;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import com.okcompu.ecommerce.backendokcompu.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> findAll() {
        List<ProductoDTO> dtos = productoService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ProductoDTO>> findAllPage(@PathVariable Integer page) {
        Page<ProductoDTO> dtos = productoService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> findById(@PathVariable("id") Long id) {
        Producto obj = productoService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductoDTO dto) {
        Producto obj = productoService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdProducto()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> update(@PathVariable Long id,@Valid @RequestBody ProductoDTO dto) {
        Producto obj = productoService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Producto convertEntity(ProductoDTO dto) {
        return mapper.map(dto, Producto.class);
    }

    private ProductoDTO converDTO(Producto producto) {
        return mapper.map(producto, ProductoDTO.class);
    }
}
