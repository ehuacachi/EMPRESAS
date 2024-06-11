package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.ComprobanteDTO;
import com.okcompu.ecommerce.backendokcompu.model.Comprobante;
import com.okcompu.ecommerce.backendokcompu.service.ComprobanteService;
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
@RequestMapping("/comprobantes")
@RequiredArgsConstructor
public class ComprobanteController {

    private final ComprobanteService comprobanteService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ComprobanteDTO>> findAll() {
        List<ComprobanteDTO> dtos = comprobanteService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<ComprobanteDTO>> findAllPage(@PathVariable Integer page) {
        Page<ComprobanteDTO> dtos = comprobanteService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> findById(@PathVariable("id") Long id) {
        Comprobante obj = comprobanteService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ComprobanteDTO dto) {
        Comprobante obj = comprobanteService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdComprobante()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComprobanteDTO> update(@PathVariable Long id,@Valid @RequestBody ComprobanteDTO dto) {
        Comprobante obj = comprobanteService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        comprobanteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Comprobante convertEntity(ComprobanteDTO dto) {
        return mapper.map(dto, Comprobante.class);
    }

    private ComprobanteDTO converDTO(Comprobante comprobante) {
        return mapper.map(comprobante, ComprobanteDTO.class);
    }
}
