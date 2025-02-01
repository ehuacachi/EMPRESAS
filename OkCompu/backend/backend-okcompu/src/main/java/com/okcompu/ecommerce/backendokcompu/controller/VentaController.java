package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.VentaDTO;
import com.okcompu.ecommerce.backendokcompu.model.DetalleVenta;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.Venta;
import com.okcompu.ecommerce.backendokcompu.service.ProductoAlmacenService;
import com.okcompu.ecommerce.backendokcompu.service.VentaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final ProductoAlmacenService productoAlmacenService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<VentaDTO>> findAll() {
        List<VentaDTO> dtos = ventaService.findAll().stream().map(this::convertDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<VentaDTO>> findAllPage(@PathVariable Integer page) {
        Page<VentaDTO> dtos = ventaService.findAll(PageRequest.of(page,4 )).map(this::convertDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> findById(@PathVariable("id") Long id) {
        Venta obj = ventaService.findById(id);
        return ResponseEntity.ok(convertDTO(obj));
    }

    @PostMapping
    public ResponseEntity<VentaDTO> registrarVenta(@Valid @RequestBody VentaDTO ventaDTO) {
        Venta venta = ventaService.registrarVenta(ventaDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(venta.getIdVenta())
                .toUri();

        return ResponseEntity.created(location).body(this.convertDTO(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<VentaDTO> anularVenta(@PathVariable Long id) {
        return ResponseEntity.ok(this.convertDTO(ventaService.anularVenta(id)));
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<VentaDTO>> buscarPorFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<VentaDTO> ventas = ventaService.buscarPorFechas(fechaInicio, fechaFin)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<VentaDTO>> buscarPorCliente(@PathVariable Long idCliente) {
        List<VentaDTO> ventas = ventaService.buscarPorCliente(idCliente)
                .stream()
                .map(this::convertDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ventas);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<VentaDTO> update(@PathVariable Long id,@Valid @RequestBody VentaDTO dto) {
//        Venta obj = ventaService.update(id,convertEntity(dto));
//        return  ResponseEntity.ok(converDTO(obj));
//    }



//    @DeleteMapping("{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id){
//        ventaService.delete(id);
//        return ResponseEntity.noContent().build();
//    }



    private Venta convertEntity(VentaDTO dto) {
        return mapper.map(dto, Venta.class);
    }

    private VentaDTO convertDTO(Venta venta) {
        return mapper.map(venta, VentaDTO.class);
    }
}
