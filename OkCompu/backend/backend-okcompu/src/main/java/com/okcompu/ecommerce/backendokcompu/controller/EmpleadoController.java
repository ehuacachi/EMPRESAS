package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.EmpleadoDTO;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.service.EmpleadoService;
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
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Qualifier("empleadoMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
        List<EmpleadoDTO> dtos = empleadoService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<EmpleadoDTO>> findAllPage(@PathVariable Integer page) {
        Page<EmpleadoDTO> dtos = empleadoService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable("id") Long id) {
        Empleado obj = empleadoService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody EmpleadoDTO dto) {
        Empleado obj = empleadoService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdEmpleado()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Long id,@Valid @RequestBody EmpleadoDTO dto) {
        Empleado obj = empleadoService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Empleado convertEntity(EmpleadoDTO dto) {
        return mapper.map(dto, Empleado.class);
    }

    private EmpleadoDTO converDTO(Empleado empleado) {
        return mapper.map(empleado, EmpleadoDTO.class);
    }
}
