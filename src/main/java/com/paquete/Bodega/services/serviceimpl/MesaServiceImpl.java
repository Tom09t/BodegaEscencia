package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Mesa;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.MesaRepository;
import com.paquete.Bodega.services.service.MesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MesaServiceImpl extends BaseServiceImpl<Mesa, Long> implements MesaService {

    //Inyeccion de dependencias
    @Autowired
    MesaRepository mesaRepository;

    //Configuracion necesaria
    public MesaServiceImpl(BaseRepository<Mesa, Long> baseRepository, MesaRepository mesaRepository) {
        super(baseRepository);
        this.mesaRepository = mesaRepository;
    }
}
