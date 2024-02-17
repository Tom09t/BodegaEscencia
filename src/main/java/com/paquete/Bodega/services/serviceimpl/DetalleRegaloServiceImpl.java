package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.DetalleRegalo;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.models.Regalo;
import com.paquete.Bodega.repository.DetalleRegaloRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.repository.RegaloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class DetalleRegaloServiceImpl {

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    private RegaloRepository regaloRepository;
    @Autowired
    DetalleRegaloRepository detalleRegaloRepository;

    public DetalleRegalo guardarDetalleRegalo(DetalleRegalo detalleRegalo) throws Exception {

    return detalleRegaloRepository.save(detalleRegalo);

    }

    public DetalleRegalo buscarDetalleRegalo(Long idRegalo ,Long idDetalle) throws Exception {
        Regalo regalo = regaloRepository.findById(idRegalo).orElseThrow(()->new Exception("id no encontrado"));

        List<DetalleRegalo> detalleRegalos=regalo.getDetalleRegalos();

        for(DetalleRegalo detalle : detalleRegalos){
            if(detalle.getId()==idDetalle){
                return detalle;
            }
        }
        throw new Exception("Detalle no encontrado en la regalo con ID: " + idDetalle);
    }


    public void eliminarDetalleRegalo(Long idRegalo, Long idDetalle) throws Exception {

        Regalo regalo=regaloRepository.findById(idRegalo).orElseThrow(()->new Exception ("Regalo no encontrado " + idRegalo));
        List<DetalleRegalo> detalleRegalos=regalo.getDetalleRegalos();

        Iterator<DetalleRegalo>iterator=detalleRegalos.iterator();

        while(iterator.hasNext()){

            DetalleRegalo detalleRegalo=iterator.next();
            if(detalleRegalo.getId()==idDetalle){
                Producto producto=detalleRegalo.getProducto();
                if(producto!=null){
                    producto.setStockRegalo(producto.getStockRegalo()+detalleRegalo.getCantidad());
                }
                iterator.remove();
            detalleRegaloRepository.deleteById(idDetalle);
            return;

            }

        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idRegalo);

    }



    public void actualizarDetalleRegalo(Long idRegalo, Long idDetalle, DetalleRegalo detalleActualizado) throws Exception {
        Regalo regalo = regaloRepository.findById(idRegalo).orElseThrow(() -> new Exception("Regalo no encontrado " + idRegalo));

        for (DetalleRegalo detalleRegalo : regalo.getDetalleRegalos()) {
            if (detalleRegalo.getId()==(idDetalle)) {
                Long idProducto = detalleRegalo.getProducto().getId();
                Producto productoActualizado = productoRepository.findById(idProducto).orElseThrow(() -> new Exception("Producto no encontrado " + idProducto));


                // Actualizar el detalle del regalo
                int cantidadAnterior = detalleRegalo.getCantidad();
                int cantidadNueva = detalleActualizado.getCantidad();
                detalleRegalo.setCantidad(cantidadNueva);
                detalleRegaloRepository.save(detalleRegalo);

                // Calcular la diferencia de cantidad y ajustar el stockRegalo del producto
                int diferencia = cantidadNueva - cantidadAnterior;
                int nuevoStock = productoActualizado.getStockRegalo() - diferencia;
                if (nuevoStock < 0) {
                    // Puedes lanzar una excepción o manejarlo de alguna otra manera
                    throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el producto con ID " + idProducto);
                }
                productoActualizado.setStockRegalo(nuevoStock);
                productoRepository.save(productoActualizado);

                return;
            }
        }

        throw new Exception("Detalle de regalo no encontrado: " + idDetalle);
    }

}
