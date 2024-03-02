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
    public ResponseEntity<Venta> crearVentaConDetalles(@RequestBody VentaDto ventaDto) {
        try {
            Venta venta = ventaService.crearVentaConDetalles(ventaDto);
            return ResponseEntity.ok().body(venta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


    @GetMapping("/grupo/{id}")
    public ResponseEntity<List<Venta>> obtenerVentasDeGrupo(@PathVariable Long id) {
        List<Venta> ventas = ventaService.listarVentaDeGrupo(id);
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Venta>> ventasHistorial() {
        List<Venta> ventasFechas = ventaService.obtenerVentasOrdenadasPorFechaDesc();
        return ResponseEntity.ok().body(ventasFechas);
    }


    @DeleteMapping("/e/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id) throws Exception {
        ventaService.eliminarVenta(id);
        String mensaje = "Venta eliminada con id " + id;
        return ResponseEntity.ok(mensaje);
    }








}
