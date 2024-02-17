package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.controller.DetalleVentaController;
import com.paquete.Bodega.models.DetalleCombo;
import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.*;
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

    @Autowired
    private DetalleComboRepository detalleComboRepository;



    //Configuracion Necesaria
    public DetalleVentaServiceImpl(BaseRepository<DetalleVenta, Long> baseRepository, DetalleVentaRepository detalleVentaRepository) {
        super(baseRepository);
        this.detalleVentaRepository = detalleVentaRepository;
    }




    public DetalleVenta guardarDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }
    public DetalleCombo guardarDetalleCombo(DetalleCombo detalleVenta) {
        return detalleComboRepository.save(detalleVenta);
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

    public DetalleCombo buscarDetalleComboEnVenta(Long idVenta ,Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("id no encontrado"));

        List<DetalleCombo> detalles = venta.getDetalleCombos();
        for (DetalleCombo detalle : detalles) {
            if (detalle.getId()==idDetalle) {
                return detalle;
            }
        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idDetalle);
    }

    public void eliminarDetalle(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();

        Iterator<DetalleVenta> iterator = detalles.iterator();
        while (iterator.hasNext()) {
            DetalleVenta detalle = iterator.next();
            if (detalle.getId()==(idDetalle)) {
                Producto producto = detalle.getProducto();

                if (producto != null) {
                    producto.setStock(producto.getStock() + detalle.getCantidad());
                }
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
                Long idProducto = detalle.getProducto().getId();
                Producto productoActualizado = productoRepository.findById(idProducto).orElseThrow(() -> new Exception("Producto no encontrado " + idProducto));


                int cantidadAnterior = detalle.getCantidad();
                int cantidadNueva = detalleActualizado.getCantidad();
                detalle.setCantidad(cantidadNueva);
                detalle.setSubTotal(detalleActualizado.getSubTotal());
                detalleVentaRepository.save(detalle);

                int diferencia = cantidadNueva - cantidadAnterior;
                int nuevoStock = productoActualizado.getStock() - diferencia;

                if (nuevoStock < 0) {
                    // Puedes lanzar una excepción o manejarlo de alguna otra manera
                    throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el producto con ID " + idProducto);
                }
                productoActualizado.setStock(nuevoStock);
                productoRepository.save(productoActualizado);


                return;
            }
        }

        throw new Exception("Detalle de venta no encontrado con ID: " + idDetalle + " en la venta con ID: " + idVenta);
    }






}





