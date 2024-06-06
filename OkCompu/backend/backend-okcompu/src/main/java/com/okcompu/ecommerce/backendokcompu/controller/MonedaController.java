package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.MonedaDTO;
import com.okcompu.ecommerce.backendokcompu.model.Moneda;
import com.okcompu.ecommerce.backendokcompu.service.MonedaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monedas")
public class MonedaController {

    private final MonedaService monedaService;
    private final ModelMapper mapper;

//    EJEMPLO DE COMUNICACION DE VARIABLES
//    @Value("${moneda.controller.path}")
//    private String pathUrl;

//    @GetMapping
//    public ResponseEntity<List<MonedaDTO>> findAll() {
//        List<MonedaDTO> monedas = monedaService.findAll().stream().map(cat -> new MonedaDTO(cat.getIdMoneda(), cat.getDescripcion(), cat.getEstado())).toList();
//        return ResponseEntity.ok(monedas);
//    }

//    @GetMapping
//    public ResponseEntity<List<MonedaRecordDTO>> findAll() {
//        List<MonedaRecordDTO> monedas = monedaService.findAll().stream().map(cat -> new MonedaRecordDTO(cat.getIdMoneda(), cat.getDescripcion(), cat.getEstado())).toList();
//        return ResponseEntity.ok(monedas);
//    }

    @GetMapping
    public ResponseEntity<List<MonedaDTO>> findAll() {
        List<MonedaDTO> dtos = monedaService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<MonedaDTO>> findAllPage(@PathVariable Integer page) {
        Page<MonedaDTO> dtos = monedaService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<MonedaDTO> findById(@PathVariable("id") Long id) {
        Moneda obj = monedaService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody MonedaDTO dto) {
        Moneda obj = monedaService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdMoneda()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonedaDTO> update(@PathVariable Long id,@Valid @RequestBody MonedaDTO dto) {
        Moneda obj = monedaService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        monedaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<MonedaDTO> findByIdHateoas(@PathVariable Long id) {
        EntityModel<MonedaDTO> resource = EntityModel.of(converDTO(monedaService.findById(id)));

//        GENERAR LINK INFORMATIVOS
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());

        resource.add(link.withRel("Moneda por Id"));
        resource.add(link2.withRel("Listado de Monedas"));
        
        return resource;
    }

    private Moneda convertEntity(MonedaDTO dto) {
        return mapper.map(dto, Moneda.class);
    }

    private MonedaDTO converDTO(Moneda moneda) {
        return mapper.map(moneda, MonedaDTO.class);
    }
}
