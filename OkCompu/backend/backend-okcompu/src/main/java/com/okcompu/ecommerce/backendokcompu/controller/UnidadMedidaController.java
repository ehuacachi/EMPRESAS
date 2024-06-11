package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.UnidadMedidaDTO;
import com.okcompu.ecommerce.backendokcompu.model.UnidadMedida;
import com.okcompu.ecommerce.backendokcompu.service.UnidadMedidaService;
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
@RequestMapping("/unidadMedidas")
@RequiredArgsConstructor
public class UnidadMedidaController {

    private final UnidadMedidaService unidadMedidaService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<UnidadMedidaDTO>> findAll() {
        List<UnidadMedidaDTO> dtos = unidadMedidaService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<UnidadMedidaDTO>> findAllPage(@PathVariable Integer page) {
        Page<UnidadMedidaDTO> dtos = unidadMedidaService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UnidadMedidaDTO> findById(@PathVariable("id") Long id) {
        UnidadMedida obj = unidadMedidaService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody UnidadMedidaDTO dto) {
        UnidadMedida obj = unidadMedidaService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdUnidadMedida()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnidadMedidaDTO> update(@PathVariable Long id,@Valid @RequestBody UnidadMedidaDTO dto) {
        UnidadMedida obj = unidadMedidaService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        unidadMedidaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private UnidadMedida convertEntity(UnidadMedidaDTO dto) {
        return mapper.map(dto, UnidadMedida.class);
    }

    private UnidadMedidaDTO converDTO(UnidadMedida unidadMedida) {
        return mapper.map(unidadMedida, UnidadMedidaDTO.class);
    }
}
