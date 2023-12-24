package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.DetalleVentaRepository;
import com.paquete.Bodega.services.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta,Long> implements DetalleVentaService {

    //inyeccion de dependencias
    @Autowired
    DetalleVentaRepository detalleVentaRepository;

    //Configuracion Necesaria
    public DetalleVentaServiceImpl(BaseRepository<DetalleVenta, Long> baseRepository, DetalleVentaRepository detalleVentaRepository) {
        super(baseRepository);
        this.detalleVentaRepository = detalleVentaRepository;
    }
}
