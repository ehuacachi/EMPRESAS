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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        List<VentaDTO> dtos = ventaService.findAll().stream().map(this::converDTO).toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/page/{page}")
    public ResponseEntity<Page<VentaDTO>> findAllPage(@PathVariable Integer page) {
        Page<VentaDTO> dtos = ventaService.findAll(PageRequest.of(page,4 )).map(this::converDTO);
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO> findById(@PathVariable("id") Long id) {
        Venta obj = ventaService.findById(id);
        return ResponseEntity.ok(converDTO(obj));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody VentaDTO dto) {

        //Transformo List<DTO> a List<E>
        List<DetalleVenta> detalleVentas = mapper.map(dto.getDetalleVentas(), new TypeToken<List<DetalleVenta>>(){}.getType());
        for (DetalleVenta det : detalleVentas) {
            //Obtener el Stock del producto en el almacen de la venta
            ProductoAlmacen productoAlmacen = productoAlmacenService.findByProductoAndAlmacen(det.getProducto().getIdProducto(), convertEntity(dto).getAlmacen().getIdAlmacen());
            if (productoAlmacen != null && productoAlmacen.getCantidad() >= det.getCantidad()) {
                //Reducir el stock
                productoAlmacen.setCantidad(productoAlmacen.getCantidad() - det.getCantidad());
                productoAlmacenService.create(productoAlmacen);

            } else  {
                return ResponseEntity.badRequest().body(null);
            }

        }

        Venta obj = ventaService.create(convertEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/id").buildAndExpand(obj.getIdVenta()).toUri();
        return  ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaDTO> update(@PathVariable Long id,@Valid @RequestBody VentaDTO dto) {
        Venta obj = ventaService.update(id,convertEntity(dto));
        return  ResponseEntity.ok(converDTO(obj));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        ventaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private Venta convertEntity(VentaDTO dto) {
        return mapper.map(dto, Venta.class);
    }

    private VentaDTO converDTO(Venta venta) {
        return mapper.map(venta, VentaDTO.class);
    }
}
