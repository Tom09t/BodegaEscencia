package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class RegaloService implements ServiceImpl<Regalo,Regalo> {
    @Override
    public Regalo guardar(Regalo regalo) throws IOException {
        return null;
    }

    @Override
    public List<Regalo> listar() {
        return null;
    }

    @Override
    public Regalo buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Regalo actualizar(Regalo regalo) {
        return null;
    }
}
