package com.paquete.Bodega.controller;

import com.paquete.Bodega.DTO.VentaComboDto;
import com.paquete.Bodega.DTO.VentaDto;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.services.serviceimpl.VentaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ventas")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl>{

    @Autowired
    private VentaServiceImpl  ventaService;

    @PostMapping("/guardar")
    public ResponseEntity<String> crearVentaConDetalles(@RequestBody VentaDto ventaDto) {
        try {
            Venta venta = ventaService.crearVentaConDetalles(ventaDto);
            return new ResponseEntity<>("Venta creada exitosamente con ID: " + venta.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear la venta: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/e/{id}")
    public ResponseEntity<String>eliminarVenta(@PathVariable Long idVenta) throws Exception {

        ventaService.eliminarVenta(idVenta);
        String mensaje = "venta eliminada con id "+ idVenta;
        return ResponseEntity.ok(mensaje);

    }

    @PostMapping("/ventaCombo")
    public ResponseEntity<?> crearVentaConCombos(@RequestBody VentaComboDto ventaDTO) {
        try {
            Venta ventaGuardada = ventaService.procesarVentaConCombos(ventaDTO);
            return ResponseEntity.ok(ventaGuardada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }





}
