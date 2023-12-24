package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.VentaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ventas")
public class VentaController extends BaseControllerImpl<Venta, VentaServiceImpl>{

}
