package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpleadoEmpresaRepository;
import com.paquete.Bodega.services.service.EmpleadoEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoEmpresaServiceImpl extends BaseServiceImpl<EmpleadoEmpresa,Long> implements EmpleadoEmpresaService {

    //Inyeccion de dependencias
    @Autowired
    EmpleadoEmpresaRepository empleadoEmpresaRepository;

    //Configuracion Necesaria
    public EmpleadoEmpresaServiceImpl(BaseRepository<EmpleadoEmpresa, Long> baseRepository, EmpleadoEmpresaRepository empleadoEmpresaRepository) {
        super(baseRepository);
        this.empleadoEmpresaRepository = empleadoEmpresaRepository;
    }

}
