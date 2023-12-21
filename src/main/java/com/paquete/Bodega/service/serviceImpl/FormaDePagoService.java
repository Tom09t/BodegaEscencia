package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.FormaDePago;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class FormaDePagoService implements ServiceImpl<FormaDePago ,FormaDePago> {
    @Override
    public FormaDePago guardar(FormaDePago formaDePago) throws IOException {
        return null;
    }

    @Override
    public List<FormaDePago> listar() {
        return null;
    }

    @Override
    public FormaDePago buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public FormaDePago actualizar(FormaDePago formaDePago) {
        return null;
    }
}
