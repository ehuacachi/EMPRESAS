package com.okcompu.ecommerce.backendokcompu.service;

import com.okcompu.ecommerce.backendokcompu.dto.VentaDTO;
import com.okcompu.ecommerce.backendokcompu.model.Venta;

import java.time.LocalDateTime;
import java.util.List;

public interface VentaService extends CRUD<Venta, Long> {

    Venta registrarVenta(VentaDTO ventaDTO);
    Venta anularVenta(Long id);
    List<Venta> buscarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    List<Venta> buscarPorCliente(Long idCliente);
}
