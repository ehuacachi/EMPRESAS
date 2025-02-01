package com.okcompu.ecommerce.backendokcompu.repo;

import com.okcompu.ecommerce.backendokcompu.model.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VentaRepository extends GenericRepo<Venta, Long> {
    @Query("SELECT v FROM Venta v WHERE v.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<Venta> findByFechaBetween(
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("SELECT v FROM Venta v WHERE v.cliente.idCliente = :idCliente")
    List<Venta> findByClienteId(@Param("idCliente") Long idCliente);

}
