package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.AlmacenDTO;
import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.service.AlmacenService;
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
@RequestMapping("/almacenes")
@RequiredArgsConstructor
public class AlmacenController {

    private final AlmacenService almacenService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<AlmacenDTO>> findAll() {
        List<AlmacenDTO> dtos = almacenService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<AlmacenDTO>> findAllPage(@PathVariable Integer page) {
        Page<AlmacenDTO> dtos = almacenService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AlmacenDTO> findById(@PathVariable("id") Long id) {
        Almacen obj = almacenService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody AlmacenDTO dto) {
        Almacen obj = almacenService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdAlmacen()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlmacenDTO> update(@PathVariable Long id,@Valid @RequestBody AlmacenDTO dto) {
        Almacen obj = almacenService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        almacenService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Almacen convertEntity(AlmacenDTO dto) {
        return mapper.map(dto, Almacen.class);
    }

    private AlmacenDTO converDTO(Almacen almacen) {
        return mapper.map(almacen, AlmacenDTO.class);
    }
}
