package com.paquete.Bodega.controller;

import com.paquete.Bodega.DTO.NuevaEmpresaDto;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.services.serviceimpl.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empresa")
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaServiceImpl>{
    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;
    @PostMapping("/nuevaEmpresa")
    public ResponseEntity<?> crearEmpresa(@RequestBody NuevaEmpresaDto nuevaEmpresaDto){
        try {
            //Creamos una nueva empresa
            Empresa empresa = new Empresa();

            //Cargamos el nombre de la empresa
            empresa.setNombreEmpresa(nuevaEmpresaDto.getNombreEmpresa());

            //Cargamos los empleados de esa empresa(ya estan existentes)
            List<Long> empleadosEmpresa = nuevaEmpresaDto.getEmpleadoEmpresa();

            //bucle por cada empleadoempresa que tenga
            for (Long empleadoEmpresa: empleadosEmpresa) {
                //busco el empleado asociado a ese id
                Optional<EmpleadoEmpresa> empleadoEmpresaOptional = empleadoEmpresaRepository.findById(empleadoEmpresa);
                //si existe ese empleado lo asocio a la Empresa nueva

                if (empleadoEmpresaOptional.isPresent()){
                    EmpleadoEmpresa empleadoEmpresa1 = empleadoEmpresaOptional.get();
                    empresa.agregarEmpleadoEmpresa(empleadoEmpresa1);
                } else {
                    return ResponseEntity.badRequest().body("El empleado con el ID proporcionado no existe.");
                }
            }

            servicio.save(empresa);

            return ResponseEntity.ok("Se creo la empresa correctamente");

        }catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
