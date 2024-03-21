package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.*;
import com.paquete.Bodega.repository.DetalleRegaloRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.repository.RegaloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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

                Producto productoAntiguo = detalleRegalo.getProducto();
                if (detalleActualizado.getProducto() != null) {
                    String nombreProductoNuevo = detalleActualizado.getProducto().getNombreProducto();
                    Producto productoNuevo = productoRepository.findByNombreProducto(nombreProductoNuevo)
                            .orElseThrow(() -> new Exception("Producto no encontrado con nombre " + nombreProductoNuevo));

                    // Actualizar el producto asociado al detalle
                    detalleRegalo.setProducto(productoNuevo);


                // Actualizar el detalle del regalo
                int cantidadAnterior = detalleRegalo.getCantidad();
                    int nuevoStockAntiguo = productoAntiguo.getStockRegalo() + cantidadAnterior;
                    productoAntiguo.setStockRegalo(nuevoStockAntiguo);
                    productoRepository.save(productoAntiguo);

                int cantidadNueva = detalleActualizado.getCantidad();
                detalleRegalo.setCantidad(cantidadNueva);
                detalleRegaloRepository.save(detalleRegalo);

                // Calcular la diferencia de cantidad y ajustar el stockRegalo del producto

                int nuevoStock = productoNuevo.getStockRegalo() - cantidadNueva;
                if (nuevoStock<0) {
                    // Puedes lanzar una excepción o manejarlo de alguna otra manera
                    throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el producto con ID " + idRegalo);
                }
                productoNuevo.setStockRegalo(nuevoStock);
                productoRepository.save(productoNuevo);
                }else {
                    // Solo se está actualizando la cantidad, no se proporcionó un nuevo producto
                    int cantidadNueva = detalleActualizado.getCantidad();
                    int cantidadAnterior = detalleRegalo.getCantidad();

                    // Actualizar la cantidad y el subtotal
                    detalleRegalo.setCantidad(cantidadNueva);


                    // Ajustar el stock del producto antiguo
                    int ajusteStock = cantidadAnterior - cantidadNueva;
                    int nuevoStockAntiguo = productoAntiguo.getStockRegalo() + ajusteStock;
                    if(nuevoStockAntiguo<0){
                        throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el producto con ID " + idRegalo);
                    }

                    productoAntiguo.setStockRegalo(nuevoStockAntiguo);
                    productoRepository.save(productoAntiguo);
                }
                detalleRegaloRepository.save(detalleRegalo);




                return;
            }
        }

        throw new Exception("Detalle de regalo no encontrado: " + idDetalle);
    }

    public List<DetalleRegalo> listaDetalle(Long id) {

        Regalo regalo= regaloRepository.findById(id).orElse(null);
        if (regalo != null) {
            List<DetalleRegalo> detallesObtenidos = regalo.getDetalleRegalos();
            return detallesObtenidos;

        } else {
            System.out.println("Ventano encontrado");
            return Collections.emptyList();
        }
    }
}
