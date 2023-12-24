package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.ProductoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productos")
public class ProductoController extends BaseControllerImpl<Producto, ProductoServiceImpl>{

}
