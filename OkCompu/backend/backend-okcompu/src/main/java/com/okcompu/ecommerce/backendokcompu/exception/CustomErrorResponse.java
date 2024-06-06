package com.okcompu.ecommerce.backendokcompu.exception;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse { //Clase para retornar con campos personalizado cuando haya un error.
    private LocalDateTime dateTime;
    private String message;
    private String details;
}
