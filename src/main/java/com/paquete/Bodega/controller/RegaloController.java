package com.paquete.Bodega.controller;

import com.paquete.Bodega.DTO.RegaloDto;
import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.services.serviceimpl.FormaDePagoServiceImpl;
import com.paquete.Bodega.services.serviceimpl.RegaloServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/regalos")
public class RegaloController extends BaseControllerImpl<Regalo, RegaloServiceImpl>{


@Autowired
private RegaloServiceImpl regaloService;

    @PostMapping("/guardar")
    public ResponseEntity<Regalo> guardarRegalo(@RequestBody RegaloDto regaloDto) throws Exception {
try{
    Regalo regalo=regaloService.guardarRegalo(regaloDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(regalo);
}catch (Exception e) {
    return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
}

    }

    @DeleteMapping("/e/{id}")
    public ResponseEntity<String>eliminarRegalo(@PathVariable Long id) throws Exception {

        regaloService.eliminarRegalo(id);
        String mensaje ="Regalo eliminado con id " + id;
        return ResponseEntity.ok((mensaje));
    }


}
