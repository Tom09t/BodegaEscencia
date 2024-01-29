package com.paquete.Bodega.controller;
import com.paquete.Bodega.DTO.NuevoGrupoDto;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.serviceimpl.GrupoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/grupos")
public class GrupoController extends BaseControllerImpl<Grupo, GrupoServiceImpl>{

    @Autowired
    EmpresaRepository empresaRepository;

    @PostMapping("/nuevoGrupo")
    public ResponseEntity<?> crearGrupo(@RequestBody NuevoGrupoDto nuevoGrupoDto) {

        try{
            //Creamos un nuevo grupo
            Grupo grupo = new Grupo();

            //Seteamos los datos en el grupo
            grupo.setComensales(nuevoGrupoDto.getComensales());

            // Buscamos la empresa por su ID
            Optional<Empresa> empresaOptional = empresaRepository.findById(nuevoGrupoDto.getEmpresa());

            if (empresaOptional.isPresent()) {
                // Si la empresa existe, la asignamos al grupo
                Empresa empresaSeleccionada = empresaOptional.get();
                grupo.setEmpresa(empresaSeleccionada);

                // Inicializamos algunos datos
                grupo.setMontoMesa(0.0);
                grupo.setEstadoGrupo(EstadoGrupo.Abierto);

                // Guardamos el grupo
                servicio.save(grupo);

                return ResponseEntity.ok("Grupo creado exitosamente.");
            } else {
                // Si no se encuentra la empresa, puedes manejar el caso en consecuencia
                return ResponseEntity.badRequest().body("La empresa con el ID proporcionado no existe.");
            }


        } catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
