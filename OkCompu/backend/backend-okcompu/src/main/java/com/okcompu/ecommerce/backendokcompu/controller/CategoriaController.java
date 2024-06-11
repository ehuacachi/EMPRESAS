package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.CategoriaDTO;
import com.okcompu.ecommerce.backendokcompu.model.Categoria;
import com.okcompu.ecommerce.backendokcompu.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

//    EJEMPLO DE COMUNICACION DE VARIABLES
//    @Value("${categoria.controller.path}")
//    private String pathUrl;

//    @GetMapping
//    public ResponseEntity<List<CategoriaDTO>> findAll() {
//        List<CategoriaDTO> categorias = categoriaService.findAll().stream().map(cat -> new CategoriaDTO(cat.getIdCategoria(), cat.getDescripcion(), cat.getEstado())).toList();
//        return ResponseEntity.ok(categorias);
//    }

//    @GetMapping
//    public ResponseEntity<List<CategoriaRecordDTO>> findAll() {
//        List<CategoriaRecordDTO> categorias = categoriaService.findAll().stream().map(cat -> new CategoriaRecordDTO(cat.getIdCategoria(), cat.getDescripcion(), cat.getEstado())).toList();
//        return ResponseEntity.ok(categorias);
//    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> findAll() {
        List<CategoriaDTO> dtos = categoriaService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<CategoriaDTO>> findAllPage(@PathVariable Integer page) {
        Page<CategoriaDTO> dtos = categoriaService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> findById(@PathVariable("id") Long id) {
        Categoria obj = categoriaService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody CategoriaDTO dto) {
        Categoria obj = categoriaService.create(convertEntity(dto));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdCategoria()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> update(@PathVariable Long id,@Valid @RequestBody CategoriaDTO dto) {
        Categoria obj = categoriaService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<CategoriaDTO> findByIdHateoas(@PathVariable Long id) {
        EntityModel<CategoriaDTO> resource = EntityModel.of(converDTO(categoriaService.findById(id)));

//        GENERAR LINK INFORMATIVOS
        WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).findAll());

        resource.add(link.withRel("Categoria por Id"));
        resource.add(link2.withRel("Listado de Categorias"));
        
        return resource;
    }

    private Categoria convertEntity(CategoriaDTO dto) {
        return mapper.map(dto, Categoria.class);
    }

    private CategoriaDTO converDTO(Categoria categoria) {
        return mapper.map(categoria, CategoriaDTO.class);
    }
}
