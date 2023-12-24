package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.FormaDePagoRepository;
import com.paquete.Bodega.services.service.FormaDePagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormaDePagoServiceImpl extends BaseServiceImpl<FormaDePago, Long> implements FormaDePagoService {

    //inyeccion de dependencia
    @Autowired
    FormaDePagoRepository formaDePagoRepository;

    //Configuracion necesaria
    public FormaDePagoServiceImpl(BaseRepository<FormaDePago, Long> baseRepository, FormaDePagoRepository formaDePagoRepository) {
        super(baseRepository);
        this.formaDePagoRepository = formaDePagoRepository;
    }
}
