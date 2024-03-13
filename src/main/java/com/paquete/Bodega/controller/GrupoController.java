package com.paquete.Bodega.controller;
import com.paquete.Bodega.DTO.NuevoGrupoDto;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.serviceimpl.GrupoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/grupos")
public class GrupoController extends BaseControllerImpl<Grupo, GrupoServiceImpl>{

@Autowired
private GrupoServiceImpl grupoService;

    @PostMapping("/CrearNuevoGrupoRestaurante")
    public ResponseEntity<?> crearNuevoGrupoRestaurante(@RequestBody NuevoGrupoDto nuevoGrupoDto){
        try{
            grupoService.crearGrupoRestaurante(nuevoGrupoDto);
            return ResponseEntity.ok("Grupo creado exitosamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el grupo: " + e.getMessage());
        }
    }

    @PostMapping("/CrearNuevoGrupoWine")
    public ResponseEntity<?> crearNuevoGrupoWine(@RequestBody NuevoGrupoDto nuevoGrupoDto){
        try{
            grupoService.crearGrupoWine(nuevoGrupoDto);
            return ResponseEntity.ok("Grupo creado exitosamente");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el grupo: " + e.getMessage());
        }
    }

    @GetMapping("/listR")
    public ResponseEntity<List<Grupo>> obtenerGruposConTotales() {
        List<Grupo> gruposConTotales = grupoService.listarGruposRestaurante();
        return new ResponseEntity<>(gruposConTotales, HttpStatus.OK);
    }
    @GetMapping("/listW")
    public ResponseEntity<List<Grupo>> obtenerGruposConTotalesWine() {
        List<Grupo> gruposConTotales = grupoService.listarGruposWine();
        return new ResponseEntity<>(gruposConTotales, HttpStatus.OK);
    }

    @PutMapping("/{id}/a")
    public ResponseEntity<?> actualizarGrupo(@PathVariable Long id, @RequestBody NuevoGrupoDto grupoRequest) {
        Grupo grupoActualizado = grupoService.actualizarGrupo(id, grupoRequest);

        if (grupoActualizado != null) {
            return new ResponseEntity<>(grupoActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No se encontró el grupo con ID: " + id, HttpStatus.NOT_FOUND);
        }
    }





}
