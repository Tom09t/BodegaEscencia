package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.DetalleVentaRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.DetalleVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta,Long> implements DetalleVentaService {

    //inyeccion de dependencias
    @Autowired
    DetalleVentaRepository detalleVentaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    private VentaRepository ventaRepository;



    //Configuracion Necesaria
    public DetalleVentaServiceImpl(BaseRepository<DetalleVenta, Long> baseRepository, DetalleVentaRepository detalleVentaRepository) {
        super(baseRepository);
        this.detalleVentaRepository = detalleVentaRepository;
    }


    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) throws Exception {

        Producto productoExistente = productoRepository.findById(detalleVenta.getProducto().getId()).orElse(null);
        if (productoExistente != null) {
            detalleVenta.setProducto(productoExistente);
        } else {
            // Manejar el caso donde el producto no existe
            throw new Exception("El producto no existe en la base de datos.");
        }

        if(productoExistente.getStock()<detalleVenta.getCantidad()) {
            throw new Exception("No hay suficiente stock");
        }

        Double precioConCantidad=detalleVenta.getCantidad()*productoExistente.getPrecio();
        detalleVenta.setSubTotal(precioConCantidad);


        return detalleVentaRepository.save(detalleVenta);


    }


}
