package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.ComboVentaDto;
import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.DTO.VentaComboDto;
import com.paquete.Bodega.DTO.VentaDto;
import com.paquete.Bodega.models.*;
import com.paquete.Bodega.repository.*;
import com.paquete.Bodega.services.service.VentaService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    //Inyeccion De dependencias
    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    GrupoRepository grupoRepository;
    @Autowired
    private DetalleComboRepository detalleComboRepository;
    @Autowired
    private DetalleVentaServiceImpl detalleService;

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private ComboServiceImpl comboService;


    @Autowired
    ComboRepository comboRepository;

    //Configuraciones necesarias
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository = ventaRepository;
    }


    @Transactional(rollbackOn = RuntimeException.class)
    public Venta crearVentaConDetalles(VentaDto ventaDTO) throws Exception {

        try {
            Long grupoId = ventaDTO.getGrupoId();
            Grupo grupoExistente = grupoRepository.findById(grupoId)
                    .orElseThrow(() -> new Exception("No se encontró el grupo con ID: " + grupoId));

            Venta nuevaVenta = new Venta();
            nuevaVenta.setTipoVenta(ventaDTO.getTipoVenta());
            nuevaVenta.setFormaPago(ventaDTO.getFormaPago());
            nuevaVenta.setGrupo(grupoExistente);
            nuevaVenta.setFechaVenta(LocalDateTime.now());

            Venta ventaGuardada = ventaRepository.save(nuevaVenta);

            List<DetalleVenta> detallesVenta = new ArrayList<>();
            List<DetalleCombo> detallesCombo = new ArrayList<>();

            for (DetalleVentaDto detalleDTO : ventaDTO.getDetalles()) {
                if ("producto".equals(detalleDTO.getTipo())) {
                    DetalleVenta detalleVenta = new DetalleVenta();
                    detalleVenta.setCantidad(detalleDTO.getCantidad());

                    Producto producto = productoRepository.findById(detalleDTO.getProductoId()).orElse(null);
                    if (producto != null) {
                        Double subtotal = detalleDTO.getCantidad() * producto.getPrecio();

                        detalleVenta.setSubTotal(subtotal);
                        detalleVenta.setProducto(producto);
                        if (!productoService.actualizarStock(detalleDTO.getProductoId(), detalleDTO.getCantidad())) {
                            throw new IllegalStateException("Stock insuficiente del producto con id " + producto.getId());
                        }
                        detalleVenta.setVenta(ventaGuardada);
                        detallesVenta.add(detalleVenta);
                    }
                } else if ("combo".equals(detalleDTO.getTipo())) {
                    DetalleCombo detalleCombo = new DetalleCombo();

                    detalleCombo.setCantidad(detalleDTO.getCantidad());

                    Combo combo = comboRepository.findById(detalleDTO.getComboId()).orElse(null);
                    if (combo != null) {
                        detalleCombo.setSubTotal(detalleDTO.getCantidad() * combo.getPrecioTotal());
                        detalleCombo.setCombo(combo);
                        if (!stockCombo(combo.getId(), detalleDTO.getCantidad())) {
                            throw new IllegalStateException("Stock insuficiente para el combo");
                        }
                        detalleCombo.setVenta(ventaGuardada);
                        detalleService.guardarDetalleCombo(detalleCombo);
                        detallesCombo.add(detalleCombo);
                    }

        }
    }
            List<Long> comboIds = comboService.obtenerCombosEnVenta(ventaDTO.getDetalles());
            boolean esCombo = !comboIds.isEmpty();
            ventaGuardada.setEsCombo(esCombo);

            if (esCombo) {
                // Si esCombo es true, entonces hay al menos un combo en la venta
                Long comboId = comboIds.get(0);  // Tomamos el primer combo encontrado
                Combo combo = comboRepository.findById(comboId).orElse(null);

                // Ahora, puedes hacer algo con el combo encontrado si es necesario
                // Por ejemplo, mostrar información o realizar alguna acción específica
            }


            ventaGuardada.setDetalles(detallesVenta);
            ventaGuardada.setDetalleCombos(detallesCombo);

            double montoVenta = detallesVenta.stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getProducto().getPrecio())
                    .sum();

            double montoVentaCombo = detallesCombo.stream()
                    .mapToDouble(detalle -> detalle.getCantidad() * detalle.getCombo().getPrecioTotal())
                    .sum();
            double resultadoFinal = montoVenta + montoVentaCombo;
            ventaGuardada.setMontoVenta(resultadoFinal);
            ventaRepository.save(ventaGuardada);

            return ventaGuardada;
        } catch (RuntimeException e) {
            throw new IllegalStateException("Error al crear la venta", e);
        }
    }

    public List<Venta> obtenerVentasOrdenadasPorFechaDesc() {
        return ventaRepository.findAllByOrderByFechaVentaDesc();
    }


    public boolean stockCombo(Long comboId, int cantidad) {

        Combo combo = comboRepository.findById(comboId).orElse(null);
        List<Producto> productosDelCombo = combo.getProductos();
        List<Integer> cantidades = combo.getCantidadesXproductos();

        Producto producto1 = null;
        Producto producto2 = null;
        Producto producto3 = null;
        Integer cantidad1 = null;
        Integer cantidad2 = null;
        Integer cantidad3 = null;

        for (int i = 0; i < productosDelCombo.size(); i++) {
            Producto productoActual = productosDelCombo.get(i);

            if (i == 0) {
                producto1 = productoActual;

            } else if (i == 1) {
                producto2 = productoActual;
            } else if (i == 2) {
                producto3 = productoActual;

            }
        }
        if (cantidades != null) {
            for (int i = 0; i < cantidades.size(); i++) {
                Integer cantidadActual = cantidades.get(i);

                if (i == 0) {
                    cantidad1 = cantidadActual;
                } else if (i == 1) {
                    cantidad2 = cantidadActual;
                } else if (i == 2) {
                    cantidad3 = cantidadActual;
                    // Puedes agregar más lógica si hay más cantidades
                }
            }


            Integer stockProducto1 = producto1.getStock() - cantidad1 * cantidad;
            if (stockProducto1 < 0) {
                return false;
            }
            producto1.setStock(stockProducto1);
            productoRepository.save(producto1);

            Integer stockProducto2 = producto2.getStock() - cantidad * cantidad;
            if (stockProducto2 < 0) {
                return false;
            }
            producto2.setStock(stockProducto2);
            productoRepository.save(producto2);

            Integer stockProducto3 = producto3.getStock() - cantidad3 * cantidad;
            if (stockProducto3 < 0) {
                return false;
            }
            producto3.setStock(stockProducto3);
            productoRepository.save(producto3);


        }
        return true;
    }






    public void eliminarVenta(Long idVenta) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();
        List<DetalleCombo>detalleCombos=venta.getDetalleCombos();

        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();


            if (producto != null) {
                producto.setStock(producto.getStock() + detalle.getCantidad());
                productoRepository.save(producto);
            }

            // Eliminar el detalle de la base de datos
            detalleVentaRepository.deleteById(detalle.getId());
        }
        for(DetalleCombo detalleCombo :detalleCombos){
       Combo combo=detalleCombo.getCombo();
            List<Producto> productosCombo = combo.getProductos();
            List<Integer> cantidadesXproductos = combo.getCantidadesXproductos();
       if(combo!=null){
           for (int i = 0; i < productosCombo.size(); i++) {
               Producto producto = productosCombo.get(i);
               Integer cantidadReincorporar = cantidadesXproductos.get(i);
               Integer cantidadFinal=cantidadReincorporar*detalleCombo.getCantidad();
               producto.setStock(producto.getStock() + cantidadFinal);
               productoRepository.save(producto);
           }

       }
            detalleComboRepository.deleteById(detalleCombo.getId());
        }


        // Eliminar la venta de la base de datos
        ventaRepository.deleteById(idVenta);
    }











}
