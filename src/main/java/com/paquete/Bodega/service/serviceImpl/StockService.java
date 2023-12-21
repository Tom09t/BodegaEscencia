package com.paquete.Bodega.service.serviceImpl;

import com.paquete.Bodega.models.Stock;
import com.paquete.Bodega.service.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class StockService implements ServiceImpl<Stock, Stock> {
    @Override
    public Stock guardar(Stock stock) throws IOException {
        return null;
    }

    @Override
    public List<Stock> listar() {
        return null;
    }

    @Override
    public Stock buscar(Long i) {
        return null;
    }

    @Override
    public void eliminar(Long i) throws IOException {

    }

    @Override
    public Stock actualizar(Stock stock) {
        return null;
    }
}
