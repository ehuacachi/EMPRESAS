package com.okcompu.ecommerce.backendokcompu.controller;

import com.okcompu.ecommerce.backendokcompu.dto.LoginDTO;
import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final EmpleadoService empleadoService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginRequest) {
        try {
            Empleado empleado = empleadoService.buscarPorUsername(loginRequest.getUsername());

            // Verificar contraseña
            if (passwordEncoder.matches(loginRequest.getPassword(), empleado.getPassword())) {
                // Generar token JWT o crear sesión
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
            }
        } catch (ModelNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario no encontrado");
        }
    }

}
