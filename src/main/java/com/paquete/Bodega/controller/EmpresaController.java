package com.paquete.Bodega.controller;

import com.paquete.Bodega.DTO.NuevaEmpresaDto;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.services.service.EmpresaService;
import com.paquete.Bodega.services.serviceimpl.EmpleadoEmpresaServiceImpl;
import com.paquete.Bodega.services.serviceimpl.EmpresaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empresa")
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaServiceImpl>{
    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;
    @Autowired
    private EmpresaServiceImpl empresaService;

    @Autowired
    private EmpleadoEmpresaServiceImpl empleadoEmpresaService;
    @PostMapping("/nuevaEmpresa")
    public ResponseEntity<Empresa> crearEmpresaConEmpleados(@RequestBody NuevaEmpresaDto nuevaEmpresaDto) {
        Empresa empresaCreada = empresaService.crearEmpresaConEmpleados(nuevaEmpresaDto);
        return new ResponseEntity<>(empresaCreada, HttpStatus.CREATED);
    }


    @DeleteMapping("/{empresaId}/empleados/{empleadoId}")
    public ResponseEntity<String> eliminarEmpleadoEmpresa(
            @PathVariable Long empresaId,
            @PathVariable Long empleadoId) {

        try {
            empleadoEmpresaService.eliminarEmpleadoEmpresa(empleadoId, empresaId);
            return ResponseEntity.ok("EmpleadoEmpresa eliminado correctamente.");
        } catch (HttpClientErrorException.NotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el EmpleadoEmpresa.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error al eliminar el EmpleadoEmpresa.");
        }
    }

    @PutMapping("/a/{empresaId}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long empresaId, @RequestBody NuevaEmpresaDto empresaDto) {
        Empresa empresaActualizadaGuardada = servicio.actualizarEmpresa(empresaId, empresaDto);

        if (empresaActualizadaGuardada != null) {
            return new ResponseEntity<>(empresaActualizadaGuardada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/agregarE/{empresaId}")
    public ResponseEntity<EmpleadoEmpresa> agregarEmpleado(
            @PathVariable Long empresaId,
            @RequestBody EmpleadoEmpresa empleado) {

        EmpleadoEmpresa nuevoEmpleado = servicio.agregarEmpleado(empresaId, empleado);

        if (nuevoEmpleado != null) {
            return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /*public ResponseEntity<?> crearEmpresa(@RequestBody NuevaEmpresaDto nuevaEmpresaDto){
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
    }*/
}
