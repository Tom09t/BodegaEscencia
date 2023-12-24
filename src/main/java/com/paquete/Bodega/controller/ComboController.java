package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/combos")
public class ComboController extends BaseControllerImpl<Combo, ComboServiceImpl>{
    
}
