package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.FormaPagoDTO;
import com.okcompu.ecommerce.backendokcompu.model.FormaPago;
import com.okcompu.ecommerce.backendokcompu.service.FormaPagoService;
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
@RequestMapping("/formaPagos")
@RequiredArgsConstructor
public class FormaPagoController {

    private final FormaPagoService formaPagoService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<FormaPagoDTO>> findAll() {
        List<FormaPagoDTO> dtos = formaPagoService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<FormaPagoDTO>> findAllPage(@PathVariable Integer page) {
        Page<FormaPagoDTO> dtos = formaPagoService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagoDTO> findById(@PathVariable("id") Long id) {
        FormaPago obj = formaPagoService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody FormaPagoDTO dto) {
        FormaPago obj = formaPagoService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdFormaPago()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagoDTO> update(@PathVariable Long id,@Valid @RequestBody FormaPagoDTO dto) {
        FormaPago obj = formaPagoService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        formaPagoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private FormaPago convertEntity(FormaPagoDTO dto) {
        return mapper.map(dto, FormaPago.class);
    }

    private FormaPagoDTO converDTO(FormaPago formaPago) {
        return mapper.map(formaPago, FormaPagoDTO.class);
    }
}
