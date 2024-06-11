package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.TipoDocumentoDTO;
import com.okcompu.ecommerce.backendokcompu.model.TipoDocumento;
import com.okcompu.ecommerce.backendokcompu.service.TipoDocumentoService;
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
@RequestMapping("/tipoDocumentos")
@RequiredArgsConstructor
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<TipoDocumentoDTO>> findAll() {
        List<TipoDocumentoDTO> dtos = tipoDocumentoService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<TipoDocumentoDTO>> findAllPage(@PathVariable Integer page) {
        Page<TipoDocumentoDTO> dtos = tipoDocumentoService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> findById(@PathVariable("id") Long id) {
        TipoDocumento obj = tipoDocumentoService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody TipoDocumentoDTO dto) {
        TipoDocumento obj = tipoDocumentoService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdTipoDocumento()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDocumentoDTO> update(@PathVariable Long id,@Valid @RequestBody TipoDocumentoDTO dto) {
        TipoDocumento obj = tipoDocumentoService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        tipoDocumentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private TipoDocumento convertEntity(TipoDocumentoDTO dto) {
        return mapper.map(dto, TipoDocumento.class);
    }

    private TipoDocumentoDTO converDTO(TipoDocumento tipoDocumento) {
        return mapper.map(tipoDocumento, TipoDocumentoDTO.class);
    }
}
