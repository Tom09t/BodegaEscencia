package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class VentaService implements ServiceImpl<Venta,Venta> {
    @Override
    public Venta guardar(Venta venta) throws IOException {
        return null;
    }

    @Override
    public List<Venta> listar() {
        return null;
    }

    @Override
    public Venta buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Venta actualizar(Venta venta) {
        return null;
    }
}
