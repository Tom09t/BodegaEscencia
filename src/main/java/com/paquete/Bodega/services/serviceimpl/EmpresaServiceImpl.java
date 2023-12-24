package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.services.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends BaseServiceImpl<Empresa, Long> implements EmpresaService {

    //Inyeccion de dependencias
    @Autowired
    EmpresaRepository empresaRepository;

    //Configuracion necesaria
    public EmpresaServiceImpl(BaseRepository<Empresa, Long> baseRepository, EmpresaRepository empresaRepository) {
        super(baseRepository);
        this.empresaRepository = empresaRepository;
    }

}
