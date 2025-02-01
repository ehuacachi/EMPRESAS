package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.model.Menu;
import com.okcompu.ecommerce.backendokcompu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<Menu> registrar(@Valid @RequestBody Menu menu) {
        Menu nuevoMenu = menuService.create(menu);
        return new ResponseEntity<>(nuevoMenu, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> modificar(@PathVariable Long id, @Valid @RequestBody Menu menu) {
        menu.setIdMenu(id);
        Menu menuActualizado = menuService.update(menu.getIdMenu(), menu);
        return ResponseEntity.ok(menuActualizado);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> listar() {
        List<Menu> menus = menuService.findAll();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Menu>> listarActivos() {
        List<Menu> menus = menuService.listarActivos();
        return ResponseEntity.ok(menus);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> listarPorId(@PathVariable Long id) {
        Menu menu = menuService.findById(id);
        return ResponseEntity.ok(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        menuService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Menu>> buscarPorNombre(@RequestParam String nombre) {
        List<Menu> menus = menuService.buscarPorNombre(nombre);
        return ResponseEntity.ok(menus);
    }
}
