package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.ComboVentaDto;
import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.DTO.VentaComboDto;
import com.paquete.Bodega.DTO.VentaDto;
import com.paquete.Bodega.models.*;
import com.paquete.Bodega.repository.*;
import com.paquete.Bodega.services.service.VentaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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



   public Venta crearVentaConDetalles(VentaDto ventaDTO) throws Exception {

       Long grupoId = ventaDTO.getGrupoId();

       Grupo grupoExistente = grupoRepository.findById(grupoId)
               .orElseThrow(() -> new Exception("No se encontró el grupo con ID: " + grupoId));

       Venta nuevaVenta = new Venta();
       nuevaVenta.setFechaVenta(ventaDTO.getFechaVenta());
       nuevaVenta.setFormaPago(ventaDTO.getFormaPago());
       nuevaVenta.setGrupo(grupoExistente);

       Venta ventaGuardada = ventaRepository.save(nuevaVenta);


       for (DetalleVentaDto detalleDTO : ventaDTO.getDetalles()) {


           DetalleVenta detalleVenta = new DetalleVenta();
           detalleVenta.setCantidad(detalleDTO.getCantidad());

           Producto producto = productoRepository.findById(detalleDTO.getProductoId()).orElse(null);
           Double subtotal = detalleDTO.getCantidad() * producto.getPrecio();
           detalleVenta.setSubTotal(subtotal);
           detalleVenta.setProducto(producto);
           productoService.actualizarStock(detalleDTO.getProductoId(), detalleDTO.getCantidad());
           detalleVenta.setVenta(ventaGuardada);



           // Guardar detalles de venta
           detalleService.guardarDetalleVenta(detalleVenta);
       }

       boolean esCombo = comboService.verificarComboEnVenta(ventaDTO.getDetalles());
       if(esCombo==false){
           ventaGuardada.setEsCombo(false);
           ventaGuardada.setNombreCombo("no contiene combo");
       }
       List<Long> comboIds = comboService.obtenerCombosEnVenta(ventaDTO.getDetalles());
       if (!comboIds.isEmpty()) {
           if (comboIds.size() == 1) {
               // Un solo combo encontrado
               Long comboId = comboIds.get(0);
               Combo combo = comboRepository.findById(comboId).orElse(null);

               ventaGuardada.setEsCombo(true);
               ventaGuardada.setNombreCombo("Esta venta tiene una combinacion de productos que pertenece al combo "
                       + combo.getNombreCombo() + " con el id " + combo.getId());

           } else {
               // Más de un combo encontrado
               ventaGuardada.setEsCombo(true);
               List<String> nombresCombos = new ArrayList<>();
               for (Long comboId : comboIds) {
                   Combo combo = comboRepository.findById(comboId).orElse(null);

                   if (combo != null) {
                       nombresCombos.add(combo.getNombreCombo() + " (ID: " + combo.getId() + ")");
                   }
               }
               ventaGuardada.setNombreCombo("ESTA VENTA CONTIENE VARIOS COMBOS: " + String.join(", ", nombresCombos));

           }
       }


       double montoVenta = ventaDTO.getDetalles().stream()
               .mapToDouble(detalleDTO -> detalleDTO.getCantidad() * productoRepository.findById(detalleDTO.getProductoId()).orElse(null).getPrecio())
               .sum();


       ventaGuardada.setMontoVenta(montoVenta);
       ventaRepository.save(ventaGuardada);

       return ventaGuardada;
    }
    public Venta procesarVentaConCombos(VentaComboDto ventaDTO) throws Exception {
        Long grupoId = ventaDTO.getGrupoId();
        Grupo grupoExistente = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new Exception("No se encontró el grupo con ID: " + grupoId));
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFechaVenta(ventaDTO.getFechaVenta());
        nuevaVenta.setFormaPago(ventaDTO.getFormaPago());
        nuevaVenta.setGrupo(grupoExistente);

        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        List<DetalleCombo> detalles = new ArrayList<>();

        for (ComboVentaDto comboVentaDto : ventaDTO.getCombos()) {
            DetalleCombo detalleCombo = new DetalleCombo();
            Combo combo = comboRepository.findById(comboVentaDto.getComboId())
                    .orElseThrow(() -> new Exception("Combo con ID " + comboVentaDto.getComboId() + " no encontrado."));

            detalleCombo.setVenta(ventaGuardada);
            detalleCombo.setCombo(combo);
            detalleCombo.setCantidad(comboVentaDto.getCantidad());

            // Guardar detalle de combo
            detalleService.guardarDetalleCombo(detalleCombo);

        }
        double montoTotalVenta = ventaDTO.getCombos().stream()
                .mapToDouble(comboVentaDto -> {
                    Combo combo = comboRepository.findById(comboVentaDto.getComboId()).orElse(null);
                    if (combo != null && combo.getPrecioTotal() != null) {
                        return combo.getPrecioTotal() * comboVentaDto.getCantidad();
                    }
                    return 0.0;
                })
                .sum();

        // Actualizar y guardar la venta con el monto total
        ventaGuardada.setMontoVenta(montoTotalVenta);
        ventaRepository.save(ventaGuardada);


        return ventaGuardada;

    }





       public void eliminarVenta(Long idVenta) throws Exception {
        Venta venta = ventaRepository.findById(idVenta)
                .orElseThrow(() -> new Exception("Venta no encontrada con ID: " + idVenta));

        List<DetalleVenta> detalles = venta.getDetalles();

        for (DetalleVenta detalle : detalles) {
            Producto producto = detalle.getProducto();


            if (producto != null) {
                producto.setStock(producto.getStock() + detalle.getCantidad());
                 productoRepository.save(producto);
            }

            // Eliminar el detalle de la base de datos
            detalleVentaRepository.deleteById(detalle.getId());
        }

        // Eliminar la venta de la base de datos
        ventaRepository.deleteById(idVenta);
    }









}
