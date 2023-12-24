package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Stock;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.StockServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/stock")
public class StockController extends BaseControllerImpl<Stock, StockServiceImpl>{

}
