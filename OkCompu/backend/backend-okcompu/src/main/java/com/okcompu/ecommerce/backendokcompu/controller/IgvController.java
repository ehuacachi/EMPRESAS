package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.IgvDTO;
import com.okcompu.ecommerce.backendokcompu.model.Igv;
import com.okcompu.ecommerce.backendokcompu.service.IgvService;
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
@RequestMapping("/igvs")
@RequiredArgsConstructor
public class IgvController {

    private final IgvService igvService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<IgvDTO>> findAll() {
        List<IgvDTO> dtos = igvService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<IgvDTO>> findAllPage(@PathVariable Integer page) {
        Page<IgvDTO> dtos = igvService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<IgvDTO> findById(@PathVariable("id") Long id) {
        Igv obj = igvService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody IgvDTO dto) {
        Igv obj = igvService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdIgv()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<IgvDTO> update(@PathVariable Long id,@Valid @RequestBody IgvDTO dto) {
        Igv obj = igvService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        igvService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Igv convertEntity(IgvDTO dto) {
        return mapper.map(dto, Igv.class);
    }

    private IgvDTO converDTO(Igv igv) {
        return mapper.map(igv, IgvDTO.class);
    }
}
