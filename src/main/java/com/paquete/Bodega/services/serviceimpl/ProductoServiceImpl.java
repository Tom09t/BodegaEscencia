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

    public Producto guardarProducto(Producto producto) {
        int stockActual = producto.getStock();
        int stockRegalo = producto.getStockRegalo();


        int stockActualizado = stockActual - stockRegalo;



        if (stockActualizado < 0) {
            throw new IllegalStateException("Stock insuficiente");
        }

        producto.setStock(stockActualizado);

        Producto productoGuardado = productoRepository.save(producto);



        return productoGuardado;
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
    public void actualizarStockRegalo(Long productoId, int cantidad) {
        Producto producto = productoRepository.findById(productoId).orElseThrow(() -> new NoSuchElementException("Producto no encontrado"));

        // Actualizar el stock
        int nuevoStock = producto.getStockRegalo() - cantidad;
        if (nuevoStock < 0) {
            throw new IllegalStateException("Stock insuficiente");
        }

        producto.setStockRegalo(nuevoStock);
        productoRepository.save(producto);
    }


    public Producto actualizarProducto(Producto productoActualizado) {
        // Verificar si el producto existe en la base de datos
        Producto productoExistente = productoRepository.findById(productoActualizado.getId()).orElse(null);

        if (productoExistente != null) {
            // Actualizar atributos del producto existente con los nuevos valores
            productoExistente.setFechaBajaProducto(productoActualizado.getFechaBajaProducto());
            productoExistente.setNombreProducto(productoActualizado.getNombreProducto());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setStock(productoActualizado.getStock());
            productoExistente.setStockRegalo(productoActualizado.getStockRegalo());
            // SE TIENE Q AGREGAR LOGICA PARA ACTUALIZAR LA LISTA DE COMBO O REGALOS SI HACE FALTA


            return productoRepository.save(productoExistente);
        }
return null;
    }



}
