package com.okcompu.ecommerce.backendokcompu.repo;

import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import com.okcompu.ecommerce.backendokcompu.model.EstadoEmpleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends GenericRepo<Empleado, Long> {
    Optional<Empleado> findByUsername(String username);
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByNombresContainingIgnoreCaseOrApellidoPaternoContainingIgnoreCaseOrApellidoMaternoContainingIgnoreCase(
            String nombres, String apellidoPaterno, String apellidoMaterno
    );
    List<Empleado> findByEstado(EstadoEmpleado estado);


}
