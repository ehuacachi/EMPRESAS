package com.okcompu.ecommerce.backendokcompu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//Se ha comentado @ResponseStatus(HttpStatus.NOT_FOUND), xq ya los estamos trabajando en el handler.
//@ResponseStatus(HttpStatus.NOT_FOUND)  //Con esto desactivamos que retorne un codigo 500 (postman) y a nivel de la consola solo nos muestra la excepcion mas limpia.
public class ModelNotFoundException extends RuntimeException { //RuntimeException> capturar excepcio en ejecucion

    //cuando generamos una excepcion customizada, está en la oblicacion de sobreescribir el constructor de Padre con el mensaje
    //es una excepcion customizada, cuando no se encuentre el ID
    public ModelNotFoundException(String message) {
        super(message);
    }


}
