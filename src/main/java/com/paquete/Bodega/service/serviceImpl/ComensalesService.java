package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Comensales;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ComensalesService  implements ServiceImpl<Comensales,Comensales> {
    @Override
    public Comensales guardar(Comensales comensales) throws IOException {
        return null;
    }

    @Override
    public List<Comensales> listar() {
        return null;
    }

    @Override
    public Comensales buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Comensales actualizar(Comensales comensales) {
        return null;
    }
}
