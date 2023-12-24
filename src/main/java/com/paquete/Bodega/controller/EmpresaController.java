package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.services.serviceimpl.EmpresaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empresa")
public class EmpresaController extends BaseControllerImpl<Empresa, EmpresaServiceImpl>{

}
