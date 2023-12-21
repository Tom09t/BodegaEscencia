package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.TipoVenta;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
@Service
public class TipoVentaService implements ServiceImpl<TipoVenta,TipoVenta> {
    @Override
    public TipoVenta guardar(TipoVenta tipoVenta) throws IOException {
        return null;
    }

    @Override
    public List<TipoVenta> listar() {
        return null;
    }

    @Override
    public TipoVenta buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public TipoVenta actualizar(TipoVenta tipoVenta) {
        return null;
    }
}
