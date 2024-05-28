package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.model.Categoria;
import com.okcompu.ecommerce.backendokcompu.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    @GetMapping
    public ResponseEntity<List<Categoria>> findAll() {
        List<Categoria> categorias = categoriaService.findAll();
        return ResponseEntity.ok(categorias);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<Categoria>> findAllPage(@PathVariable Integer page) {
        Page<Categoria> categoriaPage = categoriaService.findAll(PageRequest.of(page,4 ));
        return ResponseEntity.ok(categoriaPage);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable("id") Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoria);
    }
    @PostMapping
    public ResponseEntity<Categoria> save(@RequestBody Categoria categoria) {
        Categoria categoria1 = categoriaService.create(categoria);
        return  ResponseEntity.ok(categoria1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        Categoria categoria1 = categoriaService.update(id,categoria);
        return  ResponseEntity.ok(categoria1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
