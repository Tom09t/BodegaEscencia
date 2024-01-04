package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.DetalleVenta;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VentaServiceImpl extends BaseServiceImpl<Venta, Long> implements VentaService {

    //Inyeccion De dependencias
    @Autowired
    VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaServiceImpl detalleService;

    //Configuraciones necesarias
    public VentaServiceImpl(BaseRepository<Venta, Long> baseRepository, VentaRepository ventaRepository) {
        super(baseRepository);
        this.ventaRepository = ventaRepository;
    }

    public Venta crearVentaConDetalles(List<DetalleVenta> detalles) throws Exception {
        if (detalles == null || detalles.isEmpty()) {
            throw new Exception("La lista de detalles no puede estar vacía");
        }

        // Crea una nueva venta y asocia los detalles
        Venta venta = new Venta();
        venta.setFechaVenta(new Date());

        List<DetalleVenta> detallesGuardados = new ArrayList<>();

        // Crea y guarda cada detalle individualmente
        for (DetalleVenta detalle : detalles) {
            DetalleVenta detalleGuardado = detalleService.crearDetalleVenta(detalle);
            detallesGuardados.add(detalleGuardado);
        }

        // Establece la lista de detalles en la venta
        venta.setDetalles(detallesGuardados);

        // Calcula el total de la venta sumando los subtotales de los detalles
        Double totalVenta = detallesGuardados.stream()
                .mapToDouble(DetalleVenta::getSubTotal)
                .sum();

        venta.setMontoVenta(totalVenta);

        // Guarda la venta en la base de datos
        Venta ventaguardada=ventaRepository.save(venta);

        return ventaguardada;
    }

}
