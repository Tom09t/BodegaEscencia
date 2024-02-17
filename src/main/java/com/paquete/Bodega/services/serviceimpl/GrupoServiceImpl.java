package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.repository.GrupoRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoServiceImpl extends BaseServiceImpl<Grupo, Long> implements GrupoService {

    //inyeccion de dependencias
    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    //Configuracion Necesaria
    public GrupoServiceImpl(BaseRepository<Grupo, Long> baseRepository, GrupoRepository grupoRepository) {
        super(baseRepository);
        this.grupoRepository = grupoRepository;
    }


    public List<Grupo>listarGrupos(){


       List<Grupo>grupos=grupoRepository.findAll();

for(Grupo grupo:grupos){

    double totalVentas=grupo.getVentas().stream()
            .mapToDouble(Venta::getMontoVenta)
            .sum();
    grupo.setMontoGrupo(totalVentas);
}
return grupos;
    }

}
