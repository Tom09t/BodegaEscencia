package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.DetalleCombo;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.DetalleComboRepository;
import com.paquete.Bodega.services.service.VentaService;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import com.paquete.Bodega.services.serviceimpl.DetalleVentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/detallesVentas")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl>{


    @Autowired
    private DetalleVentaServiceImpl detalleService;

    @Autowired
    DetalleComboRepository detalleComboRepository;
    @GetMapping("/{idVenta}/{idDetalle}")
    public ResponseEntity<Object> buscarDetalleEnVenta(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) throws Exception {
        boolean esDetalleCombo = detalleComboRepository.existsByVentaIdAndId(idVenta, idDetalle);

        if (esDetalleCombo) {
            // Si es un detalleCombo, realizar la lógica necesaria para obtener el detalleCombo
            DetalleCombo detalleCombo = detalleService.buscarDetalleComboEnVenta(idVenta, idDetalle);
            return new ResponseEntity<>(detalleCombo, HttpStatus.OK);
        } else {
            // Si es un detalle normal, realizar la lógica necesaria para obtener el detalle
            DetalleVenta detalle = detalleService.buscarDetalleEnVenta(idVenta, idDetalle);
            return new ResponseEntity<>(detalle, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{idVenta}/{idDetalle}")
    public ResponseEntity<String> eliminarDetalleDeVenta(

            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) throws Exception {
        boolean esDetalleCombo = detalleComboRepository.existsByVentaIdAndId(idVenta, idDetalle);
        System.out.println("¿Es un detalleCombo?: " + esDetalleCombo);
        if (esDetalleCombo) {
            detalleService.eliminarDetalleCombo(idVenta, idDetalle);
            String mensaje = "Detalle con ID " + idDetalle + " eliminado de la venta con ID " + idVenta;
            return ResponseEntity.ok(mensaje);
        }

        detalleService.eliminarDetalle(idVenta, idDetalle);
        String mensaje = "Detalle con ID " + idDetalle + " eliminado de la venta con ID " + idVenta;
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{idVenta}/{idDetalle}")
    public ResponseEntity<String> actualizarDetalleVenta(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle,
            @RequestBody DetalleVenta detalleActualizado) {
        try {

            detalleService.actualizarDetalleVenta(idVenta, idDetalle, detalleActualizado);
            String mensaje = "Detalle de venta con ID " + idDetalle + " actualizado en la venta con ID " + idVenta;
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

@GetMapping("/d/{id}")
public ResponseEntity<List<DetalleVenta>>obtenerDetalles(@PathVariable Long id){
        List<DetalleVenta>detalleVentas=detalleService.listaDetalle(id);
        return ResponseEntity.ok(detalleVentas);

}


    @GetMapping("/dc/{id}")
public ResponseEntity<List<DetalleCombo>>obtenerDetallesCombo(@PathVariable Long id){
    List<DetalleCombo>detalleCombos=detalleService.listaDetalleCombo(id);
    return ResponseEntity.ok(detalleCombos);

}

    @PutMapping("/c/{idVenta}/{idDetalle}")
    public ResponseEntity<String> actualizarDetalleCombo(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle,
            @RequestBody DetalleCombo detalleActualizado) {
        try {

            detalleService.actualizarDetalleCombo(idVenta, idDetalle, detalleActualizado);
            String mensaje = "Detalle de venta con ID " + idDetalle + " actualizado en la venta con ID " + idVenta;
            return ResponseEntity.ok(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
