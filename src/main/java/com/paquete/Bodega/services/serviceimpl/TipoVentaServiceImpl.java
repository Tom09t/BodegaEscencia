package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.TipoVenta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.TipoDeVentaRepository;
import com.paquete.Bodega.services.service.TipoVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TipoVentaServiceImpl extends BaseServiceImpl<TipoVenta,Long> implements TipoVentaService {

    //inyeccion de dependencias
    @Autowired
    TipoDeVentaRepository tipoDeVentaRepository;

    //Configuraciones necesarias
    public TipoVentaServiceImpl(BaseRepository<TipoVenta, Long> baseRepository, TipoDeVentaRepository tipoDeVentaRepository) {
        super(baseRepository);
        this.tipoDeVentaRepository = tipoDeVentaRepository;
    }

}
