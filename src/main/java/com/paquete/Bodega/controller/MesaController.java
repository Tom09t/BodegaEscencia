package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Mesa;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.MesaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mesas")
public class MesaController extends BaseControllerImpl<Mesa, MesaServiceImpl>{
}
