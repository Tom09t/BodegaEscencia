package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Comision;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ComisionService implements ServiceImpl<Comision,Comision> {
    @Override
    public Comision guardar(Comision comision) throws IOException {
        return null;
    }

    @Override
    public List<Comision> listar() {
        return null;
    }

    @Override
    public Comision buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Comision actualizar(Comision comision) {
        return null;
    }
}
