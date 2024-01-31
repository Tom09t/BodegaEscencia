package com.paquete.Bodega.controller;
import com.paquete.Bodega.DTO.NuevoGrupoDto;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.serviceimpl.GrupoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/grupos")
public class GrupoController extends BaseControllerImpl<Grupo, GrupoServiceImpl>{

    @Autowired
    EmpresaRepository empresaRepository;


}
