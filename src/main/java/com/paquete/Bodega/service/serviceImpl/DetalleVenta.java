package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DetalleVenta implements ServiceImpl<DetalleVenta,DetalleVenta> {
    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) throws IOException {
        return null;
    }

    @Override
    public List<DetalleVenta> listar() {
        return null;
    }

    @Override
    public DetalleVenta buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public DetalleVenta actualizar(DetalleVenta detalleVenta) {
        return null;
    }
}
