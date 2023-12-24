package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.GrupoRepository;
import com.paquete.Bodega.services.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GrupoServiceImpl extends BaseServiceImpl<Grupo, Long> implements GrupoService {

    //inyeccion de dependencias
    @Autowired
    GrupoRepository grupoRepository;

    //Configuracion Necesaria
    public GrupoServiceImpl(BaseRepository<Grupo, Long> baseRepository, GrupoRepository grupoRepository) {
        super(baseRepository);
        this.grupoRepository = grupoRepository;
    }

}
