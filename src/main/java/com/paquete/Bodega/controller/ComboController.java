package com.paquete.Bodega.controller;

import com.paquete.Bodega.DTO.ComboDto;
import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.services.service.ComboService;
import com.paquete.Bodega.services.serviceimpl.ComboServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/combos")
public class ComboController extends BaseControllerImpl<Combo, ComboServiceImpl>{


    @Autowired
    private ComboServiceImpl comboService;

    @PostMapping("/guardar")
    public ResponseEntity<Combo> guardarCombo(@RequestBody ComboDto combo) {
        try {
            Combo comboGuardado = comboService.guardarCombo(combo);
            return ResponseEntity.ok().body(comboGuardado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}


