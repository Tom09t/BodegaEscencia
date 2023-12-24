package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.EstadoGrupo;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EstadoGrupoRepository;
import com.paquete.Bodega.services.service.EstadoGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadoGrupoServiceImpl extends BaseServiceImpl<EstadoGrupo,Long> implements EstadoGrupoService {

    //inyeccion DE dependencias
    @Autowired
    EstadoGrupoRepository estadoGrupoRepository;

    //Configuracion necesaria
    public EstadoGrupoServiceImpl(BaseRepository<EstadoGrupo, Long> baseRepository, EstadoGrupoRepository estadoGrupoRepository) {
        super(baseRepository);
        this.estadoGrupoRepository = estadoGrupoRepository;
    }
}
