package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import com.paquete.Bodega.services.serviceimpl.EmpleadoEmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empleadosEmpresa")
public class EmpleadoEmpresaController extends BaseControllerImpl<EmpleadoEmpresa, EmpleadoEmpresaServiceImpl>{


    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;

    @PostMapping("/nuevoEmpleado")
    public ResponseEntity<?> nuevoEmpleado(@RequestParam String nombre, @RequestParam Double comision) {
        try {
            // Creamos el empleado de la empresa
            EmpleadoEmpresa empleadoEmpresa = new EmpleadoEmpresa();
/*
            // Buscamos que no coincida con ningún empleado de la empresa existente
            servicio.findAll().forEach(empleadoEmpresa1 -> {
                if (empleadoEmpresa1.getNombreEmpleado().equals(nombre)) {
                    throw new RuntimeException("El Empleado ya existe");
                }
            }); */

            // Cargamos el nombre y porcentaje de la comisión
            empleadoEmpresa.setNombreEmpleado(nombre);
            empleadoEmpresa.setComision(comision);

            // Guardamos el empleado en la base de datos y retornamos respuesta
            servicio.save(empleadoEmpresa);
            return ResponseEntity.ok().body("Se guardó el empleado correctamente");

        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
