package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class EmpresaService implements ServiceImpl<Empresa, Empresa> {
    @Override
    public Empresa guardar(Empresa empresa) throws IOException {
        return null;
    }

    @Override
    public List<Empresa> listar() {
        return null;
    }

    @Override
    public Empresa buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Empresa actualizar(Empresa empresa) {
        return null;
    }
}
