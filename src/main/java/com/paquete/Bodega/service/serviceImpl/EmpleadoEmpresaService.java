package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmpleadoEmpresaService implements ServiceImpl<EmpleadoEmpresa,EmpleadoEmpresa> {
    @Override
    public EmpleadoEmpresa guardar(EmpleadoEmpresa empleadoEmpresa) throws IOException {
        return null;
    }

    @Override
    public List<EmpleadoEmpresa> listar() {
        return null;
    }

    @Override
    public EmpleadoEmpresa buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public EmpleadoEmpresa actualizar(EmpleadoEmpresa empleadoEmpresa) {
        return null;
    }
}
