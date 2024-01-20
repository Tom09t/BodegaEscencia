package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.DetalleVentaRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.DetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
public class DetalleVentaServiceImpl extends BaseServiceImpl<DetalleVenta,Long> implements DetalleVentaService {
    private List<DetalleVenta> detallesTemporales = new ArrayList<>();
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
    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }



    public DetalleVenta buscarDetalleEnVenta(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("id no encontrado"));

        List<DetalleVenta> detalles = venta.getDetalles();



        for (DetalleVenta detalle : detalles) {
            if (detalle.getId()==idDetalle) {
                return detalle;
            }
        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idDetalle);
    }
    //si se elimina un detalle se debe reincorporar el stock lo mismo con venta ,

    public void eliminarDetalle(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();

        Iterator<DetalleVenta> iterator = detalles.iterator();
        while (iterator.hasNext()) {
            DetalleVenta detalle = iterator.next();
            if (detalle.getId()==(idDetalle)) {
                iterator.remove(); // Eliminar el detalle de la lista
                detalleVentaRepository.deleteById(idDetalle); // Eliminar el detalle de la base de datos
                return;
            }
        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idVenta);
    }

    public void actualizarDetalleVenta(Long idVenta, Long idDetalle, DetalleVenta detalleActualizado) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();

        for (DetalleVenta detalle : detalles) {
            if (detalle.getId()==(idDetalle)) {

                detalle.setCantidad(detalleActualizado.getCantidad());
                detalle.setSubTotal(detalleActualizado.getSubTotal());



                detalleVentaRepository.save(detalle);
                return;
            }
        }

        throw new Exception("Detalle de venta no encontrado con ID: " + idDetalle + " en la venta con ID: " + idVenta);
    }






}





