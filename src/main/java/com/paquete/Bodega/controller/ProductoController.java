package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.ProductoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productos")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{

    @Autowired
    ProductoServiceImpl productoService;


    @PostMapping("/guardar")
    public ResponseEntity<?> guardarProducto(@RequestBody Producto producto) {
        try {

            Producto productoGuardado = productoService.guardarProducto(producto);


            return ResponseEntity.status(HttpStatus.CREATED).body(productoGuardado);
        } catch (IllegalStateException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar la solicitud");
        }
    }

    @PutMapping("/a/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        try {
            productoActualizado.setId(id);
         Producto producto= productoService.actualizarProducto(productoActualizado);

            return ResponseEntity.ok().body(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
