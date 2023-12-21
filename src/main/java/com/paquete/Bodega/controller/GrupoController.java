package com.paquete.Bodega.controller;

import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.service.serviceImpl.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @PostMapping
    public ResponseEntity<Grupo>guardarGrupo (@RequestBody Grupo grupo) throws IOException {

      Grupo grupoGuardado= grupoService.guardar(grupo);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/mensaje")
    public ResponseEntity<String>stringResponseEntity(){
        return ResponseEntity.ok().body("HOLAAA");
}

}
