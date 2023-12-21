package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Mesa;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MesaService implements ServiceImpl<Mesa, Mesa> {
    @Override
    public Mesa guardar(Mesa mesa) throws IOException {
        return null;
    }

    @Override
    public List<Mesa> listar() {
        return null;
    }

    @Override
    public Mesa buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Mesa actualizar(Mesa mesa) {
        return null;
    }
}
