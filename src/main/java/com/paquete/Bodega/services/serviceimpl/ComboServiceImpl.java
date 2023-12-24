package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.ComboRepository;
import com.paquete.Bodega.services.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboServiceImpl extends BaseServiceImpl<Combo,Long> implements ComboService {

    //inyeccion de dependencias
    @Autowired
    private ComboRepository comboRepository;

    //Configuracion necesaria
    public ComboServiceImpl(BaseRepository<Combo, Long> baseRepository, ComboRepository comboRepository) {
        super(baseRepository);
        this.comboRepository = comboRepository;
    }

}
