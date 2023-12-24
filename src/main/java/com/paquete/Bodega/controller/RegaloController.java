package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.RegaloServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/regalos")
public class RegaloController extends BaseControllerImpl<Regalo, RegaloServiceImpl>{

}
