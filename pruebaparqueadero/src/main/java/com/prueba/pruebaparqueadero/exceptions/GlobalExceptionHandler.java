package com.prueba.pruebaparqueadero.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ConflictException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleConflictException(ConflictException e){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", ""+e.getMessage()));

    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", ""+e.getMessage()));
    }
}
