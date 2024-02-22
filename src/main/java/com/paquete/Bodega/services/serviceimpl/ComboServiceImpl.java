package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.ComboDto;
import com.paquete.Bodega.DTO.DetalleVentaDto;
import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.Producto;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.ComboRepository;
import com.paquete.Bodega.repository.ProductoRepository;
import com.paquete.Bodega.services.service.ComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComboServiceImpl extends BaseServiceImpl<Combo,Long> implements ComboService {

    //inyeccion de dependencias
    @Autowired
    private ComboRepository comboRepository;

    @Autowired
    private ProductoRepository productoRepository;

    //Configuracion necesaria
    public ComboServiceImpl(BaseRepository<Combo, Long> baseRepository, ComboRepository comboRepository) {
        super(baseRepository);
        this.comboRepository = comboRepository;
    }


    public Combo guardarCombo(ComboDto comboDTO) throws Exception {

        if (comboRepository.existsByNombreCombo(comboDTO.getNombreCombo())) {
            throw new Exception("Ya existe un Combo con el nombre: " + comboDTO.getNombreCombo());
        }
     double precio1;
     double precio2;
     double precio3;

        // Verificar la existencia de los productos del combo
        List<Long> productosIds = comboDTO.getProductosIds();
        List<Producto> productosCombo = new ArrayList<>();

        for (Long productoId : productosIds) {
            Producto productoExistente = productoRepository.findById(productoId)
                    .orElseThrow(() -> new Exception("Producto con ID " + productoId + " no encontrado."));
            productosCombo.add(productoExistente);

        }

    Producto producto1 = productosCombo.get(0);
    Producto producto2 = productosCombo.get(1);
    Producto producto3 = productosCombo.get(2);

        if (comboDTO.getCantidad1() <= 0 || comboDTO.getCantidad2() <= 0 || comboDTO.getCantidad3() <= 0) {
            throw new Exception("Las cantidades deben ser mayores que cero.");
        }

    precio1=producto1.getPrecio()*comboDTO.getCantidad1();
    precio2=producto2.getPrecio()*comboDTO.getCantidad2();
    precio3=producto3.getPrecio()*comboDTO.getCantidad3();

        int cantidad1 = comboDTO.getCantidad1();
        int cantidad2 = comboDTO.getCantidad2();
        int cantidad3 = comboDTO.getCantidad3();

  List<Integer>cantidadesXproductos= new ArrayList<>();
        cantidadesXproductos.add(cantidad1);
        cantidadesXproductos.add(cantidad2);
        cantidadesXproductos.add(cantidad3);

    double total=precio1+precio2+precio3;
        // Guardar el combo y sus relaciones
        Combo combo = new Combo();

        combo.setNombreCombo(comboDTO.getNombreCombo());
        combo.setProductos(new ArrayList<>(productosCombo));
        combo.setPrecioTotal(total);
        combo.setCantidadesXproductos(cantidadesXproductos);

        return comboRepository.save(combo);
    }


    public List<Long> obtenerCombosEnVenta(List<DetalleVentaDto> detallesVenta) {
        // Obtener todos los combos existentes
        List<Combo> combos = comboRepository.findAll();

        List<Long> combosEncontrados = new ArrayList<>();

        for (Combo combo : combos) {
            // Verificar si todos los productos del combo están presentes en la venta
            if (verificarProductosEnVenta(combo, detallesVenta)) {
                combosEncontrados.add(combo.getId());
            }
        }

        return combosEncontrados;
    }
    private boolean verificarProductosEnVenta(Combo combo, List<DetalleVentaDto> detallesVenta) {
        List<Long> productosComboIds = combo.getProductos().stream().map(Producto::getId).collect(Collectors.toList());
        List<Long> productosVentaIds = detallesVenta.stream().map(DetalleVentaDto::getProductoId).collect(Collectors.toList());

        if (!productosVentaIds.containsAll(productosComboIds)) {
            return false;
        }
        for (int i = 0; i < productosComboIds.size(); i++) {
            Long productoId = productosComboIds.get(i);
            int cantidadCombo = combo.getCantidadesXproductos().get(i);
            int cantidadVenta = detallesVenta.stream()
                    .filter(detalle -> detalle.getProductoId().equals(productoId))
                    .mapToInt(DetalleVentaDto::getCantidad)
                    .sum();

            if (cantidadCombo != cantidadVenta) {
                return false;
            }
        }
        // Verificar si todos los productos del combo están presentes en la venta
        return true;
    }


    public boolean verificarComboEnVenta(List<DetalleVentaDto> detallesVenta) {
        // Obtener todos los combos existentes
        List<Combo> combos = comboRepository.findAll();

        for (Combo combo : combos) {
            // Verificar si los productos y cantidades coinciden
            if (verificarProductosParaCombo(combo, detallesVenta)) {

                System.out.println("Combo reconocido: " + combo.getNombreCombo() + combo.getId());
                return true; // Combo reconocido
            }
        }

        return false; // No se encontró ningún combo
    }
    private boolean verificarProductosParaCombo(Combo combo, List<DetalleVentaDto> detallesVenta) {
        List<Producto> productosCombo = new ArrayList<>(combo.getProductos());
        List<Long> productosComboIds = productosCombo.stream().map(Producto::getId).collect(Collectors.toList());

        for (DetalleVentaDto detalleVenta : detallesVenta) {
            Long productoId = detalleVenta.getProductoId();
            if (productosComboIds.contains(productoId)) {
                // Si el producto pertenece al combo, verificar si hay suficientes
                Producto productoCombo = productosCombo.stream().filter(p -> p.getId()==(productoId)).findFirst().orElse(null);
                if (productoCombo != null && detalleVenta.getCantidad() >= productoCombo.getStock()) {
                    // Suficientes productos para formar el combo
                    return true;
                }
            }
        }

        return false;
    }


    private boolean verificarProductosEnCombo(Combo combo, List<DetalleVentaDto> detallesVenta) {
        List<Producto> productosCombo = new ArrayList<>(combo.getProductos());
        List<Long> productosComboIds = productosCombo.stream().map(Producto::getId).collect(Collectors.toList());

        List<Long> productosVentaIds = detallesVenta.stream().map(DetalleVentaDto::getProductoId).collect(Collectors.toList());

        // Verificar si los productos y cantidades coinciden exactamente
        return productosComboIds.containsAll(productosVentaIds) && productosVentaIds.containsAll(productosComboIds);
    }

    public List<Long> devolverCombos(List<DetalleVentaDto> detallesVenta) {
        List<Long> idsCombos = new ArrayList<>();

        // Obtener todos los combos existentes
        List<Combo> combos = comboRepository.findAll();

        for (Combo combo : combos) {
            // Verificar si los productos y cantidades coinciden
            if (verificarProductosEnCombo(combo, detallesVenta)) {
                System.out.println("Combo reconocido: " + combo.getNombreCombo() + combo.getId());
                idsCombos.add(combo.getId());
            }
        }

        return idsCombos;
    }

}
