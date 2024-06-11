package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.MarcaDTO;
import com.okcompu.ecommerce.backendokcompu.model.Marca;
import com.okcompu.ecommerce.backendokcompu.service.MarcaService;
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
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<MarcaDTO>> findAll() {
        List<MarcaDTO> dtos = marcaService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<MarcaDTO>> findAllPage(@PathVariable Integer page) {
        Page<MarcaDTO> dtos = marcaService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> findById(@PathVariable("id") Long id) {
        Marca obj = marcaService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MarcaDTO dto) {
        Marca obj = marcaService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdMarca()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> update(@PathVariable Long id,@Valid @RequestBody MarcaDTO dto) {
        Marca obj = marcaService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        marcaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Marca convertEntity(MarcaDTO dto) {
        return mapper.map(dto, Marca.class);
    }

    private MarcaDTO converDTO(Marca marca) {
        return mapper.map(marca, MarcaDTO.class);
    }
}
