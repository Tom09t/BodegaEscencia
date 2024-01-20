package com.paquete.Bodega.services.serviceimpl;


import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.services.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductoServiceImpl extends BaseServiceImpl<Producto,Long> implements ProductoService{

    //inyeccion de dependencias
    @Autowired
    ProductoRepository productoRepository;

    //Configuracion necesaria
    public ProductoServiceImpl(BaseRepository<Producto, Long> baseRepository, ProductoRepository productoRepository) {
        super(baseRepository);
        this.productoRepository = productoRepository;
    }

    public void actualizarStock(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        // Actualizar el stock
        int nuevoStock = producto.getStock() - cantidad;
        if (nuevoStock < 0) {
            throw new IllegalStateException("Stock insuficiente");
        }

        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }
}
