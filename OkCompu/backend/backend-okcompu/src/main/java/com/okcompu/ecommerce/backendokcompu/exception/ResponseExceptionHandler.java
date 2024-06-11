package com.okcompu.ecommerce.backendokcompu.exception;

import org.springframework.http.*;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice   //Para intercetar las excepciones y exponerlas como un servicio REST.
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler { //Clase para interceptar las excepciones

    @ExceptionHandler(Exception.class)  //Si alguna de las excepciones no logra resolver, es ahi donde se activa el handlerAllException.
    public ResponseEntity<CustomErrorResponse> handlerAllException(Exception ex, WebRequest request) {
        //false --> obtenemos el error de manera abreviada.
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handlerModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
        //false --> obtenemos el error de manera abreviada.
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<CustomErrorResponse> handlerArithmeticException(ArithmeticException ex, WebRequest request) {
        //false --> obtenemos el error de manera abreviada.
        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String msg = ex.getBindingResult().getFieldErrors()
                .stream().map(err -> err.getField().concat(": ").concat(err.getDefaultMessage())).collect(Collectors.joining(","));

        CustomErrorResponse err = new CustomErrorResponse(LocalDateTime.now(), msg, request.getDescription(false));
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    //    @ExceptionHandler(ModelNotFoundException.class)
//    public ResponseEntity<CustomErrorRecord> handlerModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
//        //false --> obtenemos el error de manera abreviada.
//        CustomErrorRecord err = new CustomErrorRecord(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
//    }


    //DESDE SPRING BOOT 3
//    @ExceptionHandler(ModelNotFoundException.class)
//    public ProblemDetail handlerModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
//        problemDetail.setTitle("Model Not found Exception");
//        problemDetail.setType(URI.create(request.getDescription(false)));
//        //adicionales
//        problemDetail.setProperty("extra1","extra-value");
//        problemDetail.setProperty("extra2",33);
//
//        return problemDetail;
//    }

//    @ExceptionHandler(ModelNotFoundException.class)
//    public ErrorResponse handlerModelNotFoundException(ModelNotFoundException ex, WebRequest request) {
//        return ErrorResponse.builder(ex, HttpStatus.NOT_FOUND, ex.getMessage())
//                .title("Model Not Found Exception")
//                .type(URI.create(request.getDescription(false)))
//                .property("extra", "extra-value")
//                .property("extra",33)
//                .build();
//    }
}
