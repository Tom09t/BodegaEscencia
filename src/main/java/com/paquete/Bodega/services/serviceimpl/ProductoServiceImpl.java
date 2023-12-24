package com.paquete.Bodega.services.serviceimpl;


import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.services.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
