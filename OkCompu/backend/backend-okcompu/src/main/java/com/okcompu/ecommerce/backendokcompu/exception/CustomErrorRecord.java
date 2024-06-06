package com.okcompu.ecommerce.backendokcompu.exception;

import java.time.LocalDateTime;

//Clase INMUTABLE, no puedes hacer un set, solo get
public record CustomErrorRecord(
        LocalDateTime dateTime,
        String message,
        String details
) {
}
