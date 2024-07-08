package com.okcompu.ecommerce.backendokcompu.config;

import com.okcompu.ecommerce.backendokcompu.dto.EmpleadoDTO;
import com.okcompu.ecommerce.backendokcompu.model.Empleado;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper") //Si los atributos del DTO y la Entidad son iguales
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }

    @Bean("empleadoMapper") // Se crea este metodo dado el o sus atributos del DTO no es igual a la Entidad
    public ModelMapper empleadoMapper(){
        ModelMapper modelMapper = new ModelMapper();

        //Proceso de ESCRITURA
        modelMapper.createTypeMap(EmpleadoDTO.class, Empleado.class)
//                .addMapping(e -> e.getNombreCompleto(), (dest, v) -> dest.setNombres(v));
                .addMapping(EmpleadoDTO::getNombreCompleto, (dest, v) -> dest.setNombres((String) v))
                .addMapping(EmpleadoDTO::getApPaterno, (dest, v) -> dest.setApellidoPaterno((String) v))
                .addMapping(EmpleadoDTO::getApMaterno, (dest, v) -> dest.setApellidoMaterno((String) v))
                .addMapping(EmpleadoDTO::getUsuario, (dest, v) -> dest.setUsername((String) v))
                .addMapping(EmpleadoDTO::getClave, (dest, v) -> dest.setPassword((String) v))
                .addMapping(EmpleadoDTO::getCorreo, (dest, v) -> dest.setEmail((String) v))
                .addMapping(EmpleadoDTO::getAvatar, (dest, v) -> dest.setFoto((String) v));


        //Proceso de LECTURA
        modelMapper.createTypeMap(Empleado.class, EmpleadoDTO.class)
//                .addMapping(e -> e.getNombres(), (dest, v) -> dest.setNombreCompleto(v))
                .addMapping(Empleado::getNombres, (dest, v) -> dest.setNombreCompleto((String) v))
                .addMapping(Empleado::getApellidoPaterno, (dest, v) -> dest.setApPaterno((String) v))
                .addMapping(Empleado::getApellidoMaterno, (dest, v) -> dest.setApMaterno((String) v))
                .addMapping(Empleado::getUsername, (dest, v) -> dest.setUsuario((String) v))
                .addMapping(Empleado::getPassword, (dest, v) -> dest.setClave((String) v))
                .addMapping(Empleado::getEmail, (dest, v) -> dest.setCorreo((String) v))
                .addMapping(Empleado::getFoto, (dest, v) -> dest.setAvatar((String) v));


        return modelMapper;
    }
}
