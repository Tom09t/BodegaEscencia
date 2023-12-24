package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    //Inyeccion De dependencias
    @Autowired
    VentaRepository ventaRepository;

    //Configuraciones necesarias
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository = ventaRepository;
    }
}
