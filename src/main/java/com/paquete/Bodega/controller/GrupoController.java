package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.services.serviceimpl.GrupoServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/grupos")
public class GrupoController extends BaseControllerImpl<Grupo, GrupoServiceImpl>{

}
