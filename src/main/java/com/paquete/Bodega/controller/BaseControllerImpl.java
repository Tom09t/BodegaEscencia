package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.BaseEntidad;
import com.paquete.Bodega.services.serviceimpl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.sql.SQLException;

public abstract class BaseControllerImpl <E extends BaseEntidad, S  extends BaseServiceImpl<E, Long>> implements BaseController<E, Long> {
    @Autowired
    protected S servicio;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.save(entity));
        } catch (SQLException e) {
            String errorMessage = e.getMessage();
            int errorCode = e.getErrorCode();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error SQL - Código: " + errorCode + ", Mensaje: " + errorMessage + "\"}");
        } catch (Exception e) {
            String errorMessage = "Error, por favor intente más tarde.";
            String exceptionMessage = e.getMessage();
            String jsonResponse = "{\"error\":\"" + errorMessage + "\", \"exceptionMessage\":\"" + exceptionMessage + "\"}";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jsonResponse);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody E entity) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, entity));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error, por favor intente más tarde.\"}");
        }
    }


}
