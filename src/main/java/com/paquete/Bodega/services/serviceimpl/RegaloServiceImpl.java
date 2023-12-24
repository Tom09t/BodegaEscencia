package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.RegaloRepository;
import com.paquete.Bodega.services.service.RegaloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegaloServiceImpl extends BaseServiceImpl<Regalo, Long> implements RegaloService {

    //Inyeccion De dependencias
    @Autowired
    RegaloRepository regaloRepository;

    //Configuraciones necesarias
    public RegaloServiceImpl(BaseRepository<Regalo, Long> baseRepository, RegaloRepository regaloRepository) {
        super(baseRepository);
        this.regaloRepository = regaloRepository;
    }

}
