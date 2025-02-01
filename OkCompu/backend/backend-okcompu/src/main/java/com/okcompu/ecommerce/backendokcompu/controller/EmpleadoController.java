package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.CambioContrasenaDTO;
import com.okcompu.ecommerce.backendokcompu.dto.EmpleadoDTO;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.service.EmpleadoService;
import com.okcompu.ecommerce.backendokcompu.util.MapperUtil;
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

//    @Qualifier("empleadoMapper")
//    private final ModelMapper mapper;

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> findAll() {
//        List<EmpleadoDTO> dtos = empleadoService.findAll().stream().map(this::converDTO).toList();
        List<EmpleadoDTO> dtos = mapperUtil.mapList(empleadoService.findAll(), EmpleadoDTO.class, "empleadoMapper");
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<Page<EmpleadoDTO>> findAllPage(@PathVariable Integer page) {
//        Page<EmpleadoDTO> dtos = empleadoService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        Page<EmpleadoDTO> dtos = empleadoService.findAll(PageRequest.of(page,4 )).map(e -> mapperUtil.map(e, EmpleadoDTO.class, "empleadoMapper"));
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> findById(@PathVariable("id") Long id) {
        Empleado obj = empleadoService.findById(id);
        return ResponseEntity.ok(mapperUtil.map(obj, EmpleadoDTO.class, "empleadoMapper"));
    }


    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody EmpleadoDTO dto) {
        Empleado obj = empleadoService.create(mapperUtil.map(dto, Empleado.class, "empleadoMapper"));
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdEmpleado()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO> update(@PathVariable Long id,@Valid @RequestBody EmpleadoDTO dto) {
        Empleado obj = empleadoService.update(id,mapperUtil.map(dto, Empleado.class, "empleadoMapper"));
        return  ResponseEntity.ok(mapperUtil.map(obj, EmpleadoDTO.class, "empleadoMapper"));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        empleadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<EmpleadoDTO> buscarPorUsername(@PathVariable String username) {
        Empleado empleado = empleadoService.buscarPorUsername(username);
        return ResponseEntity.ok(mapperUtil.map(empleado, EmpleadoDTO.class, "empleadoMapper") );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<EmpleadoDTO> buscarPorEmail(@PathVariable String email) {
        Empleado empleado = empleadoService.buscarPorEmail(email);
        return ResponseEntity.ok(mapperUtil.map(empleado, EmpleadoDTO.class, "empleadoMapper") );
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<EmpleadoDTO>> buscarPorNombreCompleto(@RequestParam String termino) {
        List<Empleado> empleados = empleadoService.buscarPorNombreCompleto(termino);
        return ResponseEntity.ok(mapperUtil.mapList(empleados, EmpleadoDTO.class, "empleadoMapper") );
    }

    @PatchMapping("/{id}/cambiar-contrasena")
    public ResponseEntity<Void> cambiarContrasena(@PathVariable Long id, @RequestBody CambioContrasenaDTO cambioContrasena) {
        empleadoService.cambiarContrasena(id, cambioContrasena.getNuevaContrasena());
        return ResponseEntity.ok().build();
    }


//    private Empleado convertEntity(EmpleadoDTO dto) {
//        return mapper.map(dto, Empleado.class);
//    }
//
//    private EmpleadoDTO converDTO(Empleado empleado) {
//        return mapper.map(empleado, EmpleadoDTO.class);
//    }
}
