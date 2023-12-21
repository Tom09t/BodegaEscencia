package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ProductoService implements ServiceImpl<Producto,Producto> {
    @Override
    public Producto guardar(Producto producto) throws IOException {
        return null;
    }

    @Override
    public List<Producto> listar() {
        return null;
    }

    @Override
    public Producto buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Producto actualizar(Producto producto) {
        return null;
    }
}
