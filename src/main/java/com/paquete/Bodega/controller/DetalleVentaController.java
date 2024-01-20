package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.services.service.VentaService;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import com.paquete.Bodega.services.serviceimpl.DetalleVentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/detallesVentas")
public class DetalleVentaController extends BaseControllerImpl<DetalleVenta, DetalleVentaServiceImpl>{


    @Autowired
    private DetalleVentaServiceImpl detalleService;

    @GetMapping("/{idVenta}/{idDetalle}")
    public ResponseEntity<DetalleVenta> buscarDetalleEnVenta(
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) throws Exception {
        DetalleVenta detalle = detalleService.buscarDetalleEnVenta(idVenta, idDetalle);
        return new ResponseEntity<>(detalle, HttpStatus.OK);
    }

    @DeleteMapping("/{idVenta}/{idDetalle}")
    public ResponseEntity<String>eliminarDetalleDeVenta (
            @PathVariable Long idVenta,
            @PathVariable Long idDetalle) throws Exception {

        detalleService.eliminarDetalle(idVenta,idDetalle);
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
}
