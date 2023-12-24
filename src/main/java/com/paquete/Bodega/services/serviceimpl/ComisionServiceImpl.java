package com.paquete.Bodega.services.serviceimpl;


import com.paquete.Bodega.models.Comision;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.ComisionRepository;
import com.paquete.Bodega.services.service.ComisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComisionServiceImpl extends BaseServiceImpl<Comision,Long> implements ComisionService {

    //inyecccion de dependencias
    @Autowired
    ComisionRepository comisionRepository;

    //Configuracion Necesaria
    public ComisionServiceImpl(BaseRepository<Comision, Long> baseRepository, ComisionRepository comisionRepository) {
        super(baseRepository);
        this.comisionRepository = comisionRepository;
    }


}
