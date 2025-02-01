package com.okcompu.ecommerce.backendokcompu.service;

import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.model.EstadoEmpleado;

import java.util.List;

public interface EmpleadoService extends CRUD<Empleado,Long> {
    // Métodos adicionales
    Empleado buscarPorUsername(String username);
    Empleado buscarPorEmail(String email);
    List<Empleado> buscarPorNombreCompleto(String termino);
    List<Empleado> listarPorEstado(EstadoEmpleado estado);
    void cambiarContrasena(Long id, String nuevaContrasena);
}
