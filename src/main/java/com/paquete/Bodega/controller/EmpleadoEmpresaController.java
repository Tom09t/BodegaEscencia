package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import com.paquete.Bodega.services.serviceimpl.EmpleadoEmpresaServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/empleadosEmpresa")
public class EmpleadoEmpresaController extends BaseControllerImpl<EmpleadoEmpresa, EmpleadoEmpresaServiceImpl>{

}
