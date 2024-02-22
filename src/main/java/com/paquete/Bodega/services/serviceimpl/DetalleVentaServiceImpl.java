package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.controller.DetalleVentaController;
import com.paquete.Bodega.models.*;
import com.paquete.Bodega.repository.*;
import com.paquete.Bodega.services.service.DetalleVentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


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
    private ProductoServiceImpl productoService;

    @Autowired
    private DetalleComboRepository detalleComboRepository;
    @Autowired
    ComboRepository comboRepository;



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



    public void eliminarDetalleCombo(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleCombo> detalleCombos = venta.getDetalleCombos();

        Iterator<DetalleCombo> iterator = detalleCombos.iterator();

        while (iterator.hasNext()) {
            DetalleCombo detalleCombo = iterator.next();

            if (detalleCombo.getId()==(idDetalle)) {
                Combo combo = detalleCombo.getCombo();

                if (combo != null) {
                    List<Producto> productosDelCombo = combo.getProductos();
                    List<Integer> cantidades = combo.getCantidadesXproductos();

                    // Iterar sobre los productos del combo
                    for (int i = 0; i < productosDelCombo.size(); i++) {
                        Producto producto = productosDelCombo.get(i);
                        Integer cantidad = cantidades.get(i)*detalleCombo.getCantidad();

                        // Aumentar el stock de cada producto según la cantidad en el combo
                       productoService.aumentarStock(producto.getId(), cantidad);
                    }
                }

                // Eliminar el detalleCombo actual
                iterator.remove();
                detalleComboRepository.deleteById(idDetalle);

                // Actualizar las listas en la entidad Venta después de la eliminación
                venta.setDetalleCombos(detalleCombos);
                ventaRepository.save(venta);

                return; // Terminar el método después de encontrar y procesar el detalleCombo
            }
        }

        throw new Exception("DetalleCombo no encontrado con ID: " + idDetalle);
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
            if (detalle.getId() == idDetalle) {
                Long idProductoAntiguo = detalle.getProducto().getId();
                Producto productoAntiguo = productoRepository.findById(idProductoAntiguo)
                        .orElseThrow(() -> new Exception("Producto no encontrado con ID " + idProductoAntiguo));

                // Obtener el nuevo producto asociado al detalle
                Long idProductoNuevo = detalleActualizado.getProducto().getId();
                Producto productoNuevo = productoRepository.findById(idProductoNuevo)
                        .orElseThrow(() -> new Exception("Producto no encontrado con ID " + idProductoNuevo));

                // Actualizar el producto asociado al detalle
                detalle.setProducto(productoNuevo);

                int cantidadAnterior = detalle.getCantidad();
                int cantidadNueva = detalleActualizado.getCantidad();
                detalle.setCantidad(cantidadNueva);


                detalle.setSubTotal(productoNuevo.getPrecio()*cantidadNueva);
                detalleVentaRepository.save(detalle);



                // Reponer el stock del producto antiguo
                int nuevoStockAntiguo = productoAntiguo.getStock() + cantidadAnterior;
                productoAntiguo.setStock(nuevoStockAntiguo);
                productoRepository.save(productoAntiguo);

                // Actualizar el stock del nuevo producto
                int nuevoStockNuevo = productoNuevo.getStock() - cantidadNueva;
                if (nuevoStockNuevo < 0) {
                    // Puedes lanzar una excepción o manejarlo de alguna otra manera
                    throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el nuevo producto con ID " + idProductoNuevo);
                }
                productoNuevo.setStock(nuevoStockNuevo);
                productoRepository.save(productoNuevo);

                return;
            }
        }

        throw new Exception("Detalle de venta no encontrado con ID: " + idDetalle + " en la venta con ID: " + idVenta);
    }




    public void actualizarDetalleCombo(Long idVenta , Long idDetalle ,DetalleCombo detalleComboActualizado) throws Exception {




        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleCombo>detalleCombos=venta.getDetalleCombos();


        for(DetalleCombo detalleCombo : detalleCombos){
            if(detalleCombo.getId()==idDetalle){

                Long idComboAntiguo=detalleCombo.getCombo().getId();
                Combo comboAntiguo=comboRepository.findById(idComboAntiguo).orElseThrow(()->new Exception("combo no encontrado" +idComboAntiguo));

                Long idComboNuevo=detalleCombo.getCombo().getId();
                Combo comboNuevo=comboRepository.findById(idComboNuevo).orElseThrow(()->new Exception("combo no encontrado" +idComboNuevo));

                detalleCombo.setCombo(comboNuevo);

                int cantidadAnterior=detalleCombo.getCantidad();
                int cantidadNueva=detalleComboActualizado.getCantidad();
                detalleCombo.setCantidad(cantidadNueva);

                detalleCombo.setSubTotal(comboNuevo.getPrecioTotal()*cantidadNueva);
                detalleComboRepository.save(detalleCombo);

                for (int i = 0; i < comboAntiguo.getProductos().size(); i++) {
                    Producto productoAntiguo = comboAntiguo.getProductos().get(i);
                    Integer cantidadAntigua = comboAntiguo.getCantidadesXproductos().get(i);
                    int nuevoStockAntiguo = productoAntiguo.getStock() + cantidadAnterior * cantidadAntigua;
                    productoAntiguo.setStock(nuevoStockAntiguo);
                    productoRepository.save(productoAntiguo);
                }
                for (int i = 0; i < comboNuevo.getProductos().size(); i++) {
                    Producto productoNuevo = comboNuevo.getProductos().get(i);
                    Integer cantidadNuevaCombo = comboNuevo.getCantidadesXproductos().get(i);
                    int nuevoStockNuevo = productoNuevo.getStock() - cantidadNueva * cantidadNuevaCombo;
                    if (nuevoStockNuevo < 0) {
                        // Puedes lanzar una excepción o manejarlo de alguna otra manera
                        throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el nuevo producto con ID " + productoNuevo.getId());
                    }
                    productoNuevo.setStock(nuevoStockNuevo);
                    productoRepository.save(productoNuevo);

                }
                return;

            }
        }
    }



}





