package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.DetalleRegalo;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.services.serviceimpl.DetalleRegaloServiceImpl;
import com.paquete.Bodega.services.serviceimpl.RegaloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/detallesRegalo")
public class DetalleRegaloController {
    @Autowired
    private RegaloServiceImpl regaloService;

    @Autowired
    private DetalleRegaloServiceImpl detalleRegaloService;


    @GetMapping("/{idRegalo}/{idDetalle}")
    public ResponseEntity<DetalleRegalo>buscarDetalleRegalo(
            @PathVariable Long idRegalo,
            @PathVariable Long idDetalle
    ) throws Exception {
     DetalleRegalo detalleRegalo=detalleRegaloService.buscarDetalleRegalo(idRegalo,idDetalle);
     return new ResponseEntity<>(detalleRegalo, HttpStatus.OK);

    }

    @GetMapping("/d/{id}")
    public ResponseEntity<List<DetalleRegalo>>obtenerDetalles(@PathVariable Long id){
        List<DetalleRegalo>detalleRegalos=detalleRegaloService.listaDetalle(id);
        return ResponseEntity.ok(detalleRegalos);

    }

    @DeleteMapping("/{idRegalo}/{idDetalle}")
    public ResponseEntity<String>elimianarDetalleRegalo(
            @PathVariable Long idRegalo,
            @PathVariable Long idDetalle
    ) throws Exception {
      detalleRegaloService.eliminarDetalleRegalo(idRegalo,idDetalle);
        String mensaje = "Detalle con ID " + idDetalle + " eliminado del regalo con ID " + idRegalo;
        return ResponseEntity.ok(mensaje);

    }

    @PutMapping("/{idRegalo}/{idDetalle}")
    public ResponseEntity<String> actualizarDetalleVenta(
            @PathVariable Long idRegalo,
            @PathVariable Long idDetalle,
            @RequestBody DetalleRegalo detalleActualizado) {
        try {
            detalleRegaloService.actualizarDetalleRegalo(idRegalo, idDetalle, detalleActualizado);
            String mensaje = "Detalle del regalo con ID " + idDetalle + " actualizado en el regalo con ID " + idRegalo;
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
