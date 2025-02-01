package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.model.EstadoEmpleado;
import com.okcompu.ecommerce.backendokcompu.repo.EmpleadoRepository;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.service.EmpleadoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoServiceImpl extends CRUDImpl<Empleado, Long> implements EmpleadoService {

    private final EmpleadoRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected GenericRepo<Empleado, Long> getRepo() {
        return repo;
    }


    @Override
    public Empleado create(Empleado empleado) {
        empleado.setPassword(passwordEncoder.encode(empleado.getPassword()));
//        empleado.setPassword(empleado.getPassword());
        empleado.setEstado(EstadoEmpleado.ACTIVO);
        return getRepo().save(empleado);
    }

    @Override
    public void delete(Long id) {
        Empleado empleado = getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));
        empleado.setEstado(EstadoEmpleado.INACTIVO);
        getRepo().save(empleado);
    }


    @Override
    public Empleado buscarPorUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new ModelNotFoundException("Empleado no encontrado"));
    }

    @Override
    public Empleado buscarPorEmail(String email) {
        return repo.findByEmail(email)
                .orElseThrow(() -> new ModelNotFoundException("Empleado no encontrado"));
    }

    @Override
    public List<Empleado> buscarPorNombreCompleto(String termino) {
        return repo.findByNombresContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCaseOrApellidoMaternoContainingIgnoreCase(
                termino, termino, termino
        );
    }

    @Override
    public List<Empleado> listarPorEstado(EstadoEmpleado estado) {
        return repo.findByEstado(estado);
    }

    @Override
    public void cambiarContrasena(Long id, String nuevaContrasena) {
        Empleado empleado = getRepo().findById(id).orElseThrow(() -> new ModelNotFoundException("ID NOT FOUND: " + id));

        // Verificar si la nueva contraseña cumple con los requisitos
        validarContrasena(nuevaContrasena);
        empleado.setPassword(passwordEncoder.encode(nuevaContrasena));
        getRepo().save(empleado);
    }

    // Método de validación de contraseña (opcional)
    private void validarContrasena(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        // Puedes agregar más validaciones
        boolean tieneNumero = password.matches(".*\\d.*");
        boolean tieneMayuscula = password.matches(".*[A-Z].*");
        boolean tieneMinuscula = password.matches(".*[a-z].*");
        boolean tieneCaracterEspecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

        if (!(tieneNumero && tieneMayuscula && tieneMinuscula && tieneCaracterEspecial)) {
            throw new IllegalArgumentException("La contraseña debe contener números, mayúsculas, minúsculas y caracteres especiales");
        }
    }

    // Método para verificar contraseña
    public boolean verificarContrasena(String passwordIngresada, String passwordAlmacenada) {
        return passwordEncoder.matches(passwordIngresada, passwordAlmacenada);
    }
}
