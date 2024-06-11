package com.okcompu.ecommerce.backendokcompu.dto;

import com.okcompu.ecommerce.backendokcompu.model.Almacen;
import com.okcompu.ecommerce.backendokcompu.model.Producto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoAlmacenDTO {

    private ProductoDTO producto;

    private AlmacenDTO almacen;

    private Integer cantidad;
}
