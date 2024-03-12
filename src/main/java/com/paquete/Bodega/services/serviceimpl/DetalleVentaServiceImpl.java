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

import java.util.*;


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


    public List<DetalleVenta> listaDetalle(Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta).orElse(null);
        if (venta != null) {
            List<DetalleVenta> detallesObtenidos = venta.getDetalles();
            return detallesObtenidos;

        } else {
            System.out.println("Ventano encontrado");
            return Collections.emptyList();
        }
    }

    public List<DetalleCombo> listaDetalleCombo(Long idVenta) {
        Venta venta = ventaRepository.findById(idVenta).orElse(null);
        if (venta != null) {
            List<DetalleCombo> detallesObtenidos = venta.getDetalleCombos();
            return detallesObtenidos;

        } else {
            System.out.println("Ventano encontrado");
            return Collections.emptyList();
        }
    }


    public DetalleVenta buscarDetalleEnVenta(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("id no encontrado"));

        List<DetalleVenta> detalles = venta.getDetalles();


        for (DetalleVenta detalle : detalles) {
            if (detalle.getId() == idDetalle) {
                return detalle;
            }
        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idDetalle);
    }
    //si se elimina un detalle se debe reincorporar el stock lo mismo con venta ,

    public DetalleCombo buscarDetalleComboEnVenta(Long idVenta, Long idDetalle) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("id no encontrado"));

        List<DetalleCombo> detalles = venta.getDetalleCombos();
        for (DetalleCombo detalle : detalles) {
            if (detalle.getId() == idDetalle) {
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

            if (detalleCombo.getId() == (idDetalle)) {
                Combo combo = detalleCombo.getCombo();

                if (combo != null) {
                    List<Producto> productosDelCombo = combo.getProductos();
                    List<Integer> cantidades = combo.getCantidadesXproductos();

                    // Iterar sobre los productos del combo
                    for (int i = 0; i < productosDelCombo.size(); i++) {
                        Producto producto = productosDelCombo.get(i);
                        Integer cantidad = cantidades.get(i) * detalleCombo.getCantidad();

                        // Aumentar el stock de cada producto según la cantidad en el combo
                        productoService.aumentarStock(producto.getId(), cantidad);
                    }
                }

                // Eliminar el detalleCombo actual
                iterator.remove();
                detalleComboRepository.deleteById(idDetalle);

                // Actualizar las listas en la entidad Venta después de la eliminación
                venta.setDetalleCombos(detalleCombos);
                actualizarMontoVentaCombo(venta);
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
            if (detalle.getId() == (idDetalle)) {
                Producto producto = detalle.getProducto();

                if (producto != null) {
                    producto.setStock(producto.getStock() + detalle.getCantidad());
                    venta.setMontoVenta(venta.getMontoVenta() - detalle.getSubTotal());
                }
                iterator.remove(); // Eliminar el detalle de la lista
                detalleVentaRepository.deleteById(idDetalle); // Eliminar el detalle de la base de datos
               actualizarMontoVenta(venta);
                ventaRepository.save(venta);
                return;
            }
        }

        throw new Exception("Detalle no encontrado en la venta con ID: " + idVenta);
    }

    private void actualizarMontoVenta(Venta venta) {
        // Calcular el nuevo montoVenta sumando los subTotal actualizados de todos los detalles
        double nuevoMontoVenta = venta.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getSubTotal())
                .sum();

        // Actualizar el montoVenta de la venta
        venta.setMontoVenta(nuevoMontoVenta);

        // Guardar cambios en la venta
        ventaRepository.save(venta);
    }
    public void actualizarDetalleVenta(Long idVenta, Long idDetalle, DetalleVenta detalleActualizado) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();

        for (DetalleVenta detalle : detalles) {
            if (detalle.getId() == idDetalle) {
                Producto productoAntiguo = detalle.getProducto();

                // Verificar si se proporcionó un nuevo producto
                if (detalleActualizado.getProducto() != null) {
                    // Obtener el nuevo producto asociado al detalle por nombre
                    String nombreProductoNuevo = detalleActualizado.getProducto().getNombreProducto();
                    Producto productoNuevo = productoRepository.findByNombreProducto(nombreProductoNuevo)
                            .orElseThrow(() -> new Exception("Producto no encontrado con nombre " + nombreProductoNuevo));

                    // Actualizar el producto asociado al detalle
                    detalle.setProducto(productoNuevo);

                    // Reponer el stock del producto antiguo
                    int cantidadAnterior = detalle.getCantidad();
                    int nuevoStockAntiguo = productoAntiguo.getStock() + cantidadAnterior;
                    productoAntiguo.setStock(nuevoStockAntiguo);
                    productoRepository.save(productoAntiguo);

                    // Actualizar la cantidad y el subtotal
                    int cantidadNueva = detalleActualizado.getCantidad();
                    detalle.setCantidad(cantidadNueva);
                    detalle.setSubTotal(productoNuevo.getPrecio() * cantidadNueva);

                    // Ajustar el stock del nuevo producto
                    int nuevoStockNuevo = productoNuevo.getStock() - cantidadNueva;
                    if (nuevoStockNuevo < 0) {
                        // Puedes lanzar una excepción o manejarlo de alguna otra manera
                        throw new IllegalStateException("La cantidad actualizada excede el stock disponible para el nuevo producto con nombre " + nombreProductoNuevo);
                    }
                    productoNuevo.setStock(nuevoStockNuevo);
                    productoRepository.save(productoNuevo);
                } else {
                    // Solo se está actualizando la cantidad, no se proporcionó un nuevo producto
                    int cantidadNueva = detalleActualizado.getCantidad();
                    int cantidadAnterior = detalle.getCantidad();

                    // Actualizar la cantidad y el subtotal
                    detalle.setCantidad(cantidadNueva);
                    detalle.setSubTotal(productoAntiguo.getPrecio() * cantidadNueva);

                    // Ajustar el stock del producto antiguo
                    int ajusteStock = cantidadAnterior - cantidadNueva;
                    int nuevoStockAntiguo = productoAntiguo.getStock() + ajusteStock;
                    productoAntiguo.setStock(nuevoStockAntiguo);
                    productoRepository.save(productoAntiguo);
                }

                detalleVentaRepository.save(detalle);
                actualizarMontoVenta(venta);
                return;
            }
        }

        throw new Exception("Detalle de venta no encontrado con ID: " + idDetalle + " en la venta con ID: " + idVenta);
    }


    private void actualizarMontoVentaCombo(Venta venta) {
        // Calcular el nuevo montoVenta sumando los subTotal actualizados de todos los detalles y detalleCombos
        double nuevoMontoVenta = venta.getDetalles().stream()
                .mapToDouble(detalle -> detalle.getSubTotal())
                .sum();

        nuevoMontoVenta += venta.getDetalleCombos().stream()
                .mapToDouble(detalleCombo -> detalleCombo.getSubTotal())
                .sum();

        // Actualizar el montoVenta de la venta
        venta.setMontoVenta(nuevoMontoVenta);

        // Guardar cambios en la venta
        ventaRepository.save(venta);
    }


    public void actualizarDetalleCombo(Long idVenta , Long idDetalle ,DetalleCombo detalleComboActualizado) throws Exception {

        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleCombo>detalleCombos=venta.getDetalleCombos();


        for(DetalleCombo detalleCombo : detalleCombos){
            if(detalleCombo.getId()==idDetalle){

              Long idComboAntiguo=detalleCombo.getCombo().getId();
                Combo comboAntiguo=comboRepository.findById(idComboAntiguo).orElseThrow(()->new Exception("combo no encontrado" +idComboAntiguo));

                String idComboNuevo=detalleCombo.getCombo().getNombreCombo();
                Combo comboNuevo=comboRepository.findByNombreCombo(idComboNuevo).orElseThrow(()->new Exception("combo no encontrado" +idComboNuevo));

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
                actualizarMontoVentaCombo(venta);
                ventaRepository.save(venta);
                return;

            }
        }
    }



}





