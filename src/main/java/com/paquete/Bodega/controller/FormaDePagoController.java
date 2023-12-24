package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/formasDePagos")
public class FormaDePagoController extends BaseControllerImpl<FormaDePago, FormaDePagoServiceImpl>{

}
