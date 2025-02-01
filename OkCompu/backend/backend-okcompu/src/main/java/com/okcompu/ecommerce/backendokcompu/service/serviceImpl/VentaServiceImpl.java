package com.okcompu.ecommerce.backendokcompu.service.serviceImpl;

import com.okcompu.ecommerce.backendokcompu.dto.DetalleVentaDTO;
import com.okcompu.ecommerce.backendokcompu.dto.VentaDTO;
import com.okcompu.ecommerce.backendokcompu.exception.ModelNotFoundException;
import com.okcompu.ecommerce.backendokcompu.exception.StockInsuficienteException;
import com.okcompu.ecommerce.backendokcompu.exception.VentaInvalidaException;
import com.okcompu.ecommerce.backendokcompu.model.DetalleVenta;
import com.okcompu.ecommerce.backendokcompu.model.ProductoAlmacen;
import com.okcompu.ecommerce.backendokcompu.model.Venta;
import com.okcompu.ecommerce.backendokcompu.repo.GenericRepo;
import com.okcompu.ecommerce.backendokcompu.repo.VentaRepository;
import com.okcompu.ecommerce.backendokcompu.service.ProductoAlmacenService;
import com.okcompu.ecommerce.backendokcompu.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl extends CRUDImpl<Venta, Long> implements VentaService {

    private final VentaRepository repo;
    private final ProductoAlmacenService productoAlmacenService;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @Override
    protected GenericRepo<Venta, Long> getRepo() {
        return repo;
    }

    @Override
    public Venta registrarVenta(VentaDTO ventaDTO) {
        try {
            if (ventaDTO.getDetalleVentas() == null || ventaDTO.getDetalleVentas().isEmpty()) {
                throw new VentaInvalidaException("La venta debe tener al menos un detalle");
            }

            // Validar stock antes de procesar la venta
            validarStock(ventaDTO);

            // Validar datos básicos
            if (ventaDTO.getCliente() == null) {
                throw new VentaInvalidaException("El cliente es requerido");
            }
            if (ventaDTO.getAlmacen() == null) {
                throw new VentaInvalidaException("El almacén es requerido");
            }

            // Crear la venta
            Venta venta = mapper.map(ventaDTO, Venta.class);
            venta.setFecha(LocalDateTime.now());

            // Calcular totales
            calcularTotales(venta);

            // Actualizar stock
            actualizarStock(venta);

            return repo.save(venta);
        } catch (StockInsuficienteException | VentaInvalidaException e){
            throw e; //Relanzar excepciones especificas
        }
        catch (Exception e) {
            throw new VentaInvalidaException("Error al registrar la venta: " + e.getMessage());
        }
    }

    private void validarStock(VentaDTO ventaDTO) {
        for (DetalleVentaDTO detalle : ventaDTO.getDetalleVentas()) {
            ProductoAlmacen productoAlmacen = productoAlmacenService
                    .findByProductoAndAlmacen(detalle.getProducto().getIdProducto(), ventaDTO.getAlmacen().getIdAlmacen());

            if (productoAlmacen == null || productoAlmacen.getCantidad() < detalle.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto ID: " + detalle.getProducto().getIdProducto());
            }
        }
    }

    private void calcularTotales(Venta venta) {
        double total = 0;
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            detalle.setSubTotal(detalle.getCantidad() * detalle.getPrecio());
            if (detalle.getDescuento() != null) {
                detalle.setSubTotal(detalle.getSubTotal() - detalle.getDescuento());
            }
            total += detalle.getSubTotal();
            detalle.setVenta(venta);
        }
        venta.setTotal(total);
    }

    private void actualizarStock(Venta venta) {
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            ProductoAlmacen productoAlmacen = productoAlmacenService
                    .findByProductoAndAlmacen(
                            detalle.getProducto().getIdProducto(),
                            venta.getAlmacen().getIdAlmacen()
                    );

            productoAlmacen.setCantidad(productoAlmacen.getCantidad() - detalle.getCantidad());
            productoAlmacenService.updateStock(
                    productoAlmacen.getProducto().getIdProducto(),
                    productoAlmacen.getAlmacen().getIdAlmacen(),
                    productoAlmacen.getCantidad()
            );
        }
    }

    @Override
    public Venta anularVenta(Long id) {
        Venta venta = findById(id);
        if (venta == null) {
            throw new ModelNotFoundException("Venta no encontrada");
        }

        // Restaurar stock
        for (DetalleVenta detalle : venta.getDetalleVentas()) {
            ProductoAlmacen productoAlmacen = productoAlmacenService
                    .findByProductoAndAlmacen(
                            detalle.getProducto().getIdProducto(),
                            venta.getAlmacen().getIdAlmacen()
                    );

            productoAlmacen.setCantidad(productoAlmacen.getCantidad() + detalle.getCantidad());
            productoAlmacenService.updateStock(
                    productoAlmacen.getProducto().getIdProducto(),
                    productoAlmacen.getAlmacen().getIdAlmacen(),
                    productoAlmacen.getCantidad()
                    );
        }

        // Marcar como anulada (podrías agregar un campo estado en la entidad Venta)
        venta.setEstado("ANULADA");
        return repo.save(venta);
    }

    @Override
    public List<Venta> buscarPorFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        if (fechaInicio == null || fechaFin == null) {
            throw new VentaInvalidaException("Las fechas de inicio y fin son requeridas");
        }

        if (fechaInicio.isAfter(fechaFin)) {
            throw new VentaInvalidaException("La fecha de inicio no puede ser posterior a la fecha fin");
        }

        try {
            return repo.findByFechaBetween(
                    fechaInicio.withHour(0).withMinute(0).withSecond(0),
                    fechaFin.withHour(23).withMinute(59).withSecond(59)
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar ventas por fechas: " + e.getMessage());
        }
    }

    @Override
    public List<Venta> buscarPorCliente(Long idCliente) {
        if (idCliente == null) {
            throw new VentaInvalidaException("El ID del cliente es requerido");
        }

        try {
            List<Venta> ventas = repo.findByClienteId(idCliente);
            if (ventas.isEmpty()) {
                throw new ModelNotFoundException("No se encontraron ventas para el cliente con ID: " + idCliente);
            }
            return ventas;
        } catch (ModelNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar ventas por cliente: " + e.getMessage());
        }
    }


}
