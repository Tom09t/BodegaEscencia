package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class ComboService implements ServiceImpl<Combo, Combo> {

    @Override
    public Combo guardar(Combo combo) throws IOException {
        return null;
    }

    @Override
    public List<Combo> listar() {
        return null;
    }

    @Override
    public Combo buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Combo actualizar(Combo combo) {
        return null;
    }
}
