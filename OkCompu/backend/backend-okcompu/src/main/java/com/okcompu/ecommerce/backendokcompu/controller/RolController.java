package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.model.Rol;
import com.okcompu.ecommerce.backendokcompu.service.RolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("roles")
@RequiredArgsConstructor
public class RolController {

    private final RolService rolService;

    @PostMapping
    public ResponseEntity<Rol> registrar(@Valid @RequestBody Rol rol) {
        Rol nuevoRol = rolService.create(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> modificar(@PathVariable Integer id, @Valid @RequestBody Rol rol) {
        rol.setIdRol(id);
        Rol rolActualizado = rolService.update(rol.getIdRol(), rol);
        return ResponseEntity.ok(rolActualizado);
    }

    @GetMapping
    public ResponseEntity<List<Rol>> listar() {
        List<Rol> roles = rolService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Rol>> listarActivos() {
        List<Rol> roles = rolService.listarActivos();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> listarPorId(@PathVariable Integer id) {
        Rol rol = rolService.findById(id);
        return ResponseEntity.ok(rol);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<Rol> buscarPorNombre(@RequestParam String nombre) {
        Rol rol = rolService.buscarPorNombre(nombre);
        return ResponseEntity.ok(rol);
    }
}
