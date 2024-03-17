package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.DTO.NuevoGrupoDto;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.Enum.TipoGrupo;
import com.paquete.Bodega.Enum.TipoVenta;
import com.paquete.Bodega.models.EmpleadoEmpresa;
import com.paquete.Bodega.models.Empresa;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.models.Venta;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.EmpresaRepository;
import com.paquete.Bodega.repository.GrupoRepository;
import com.paquete.Bodega.repository.VentaRepository;
import com.paquete.Bodega.services.service.GrupoService;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GrupoServiceImpl extends BaseServiceImpl<Grupo, Long> implements GrupoService {

    //inyeccion de dependencias
    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    //Configuracion Necesaria
    public GrupoServiceImpl(BaseRepository<Grupo, Long> baseRepository, GrupoRepository grupoRepository) {
        super(baseRepository);
        this.grupoRepository = grupoRepository;
    }


    public List<Grupo> listarGruposRestaurante() {
        List<Grupo> grupos = grupoRepository.findAll();

        return grupos.stream()
                .map(grupo -> {
                    List<Venta> ventasRestaurante = grupo.getVentas().stream()
                            .filter(venta -> venta.getTipoVenta().equals(TipoVenta.RESTAURANTE))
                            .collect(Collectors.toList());

                    double totalVentas = ventasRestaurante.stream()
                            .mapToDouble(Venta::getMontoVenta)
                            .sum();
                    if(totalVentas<0){
                        totalVentas=0;
                    }

                    grupo.setMontoVentasGrupo(totalVentas);

                    if (!ventasRestaurante.isEmpty()) { // Aplicar operaciones solo si hay ventas
                        Double totalProvisional = totalVentas + grupo.getMontoMesa();

                        double descuentoCuentaCorriente = calcularCuentaCorriente(totalProvisional, grupo.getEmpresa().getId());

                        if(descuentoCuentaCorriente<0){
                            descuentoCuentaCorriente = 0.0;
                        }
                        grupo.setDescuentoCtaCorriente(descuentoCuentaCorriente);
                        double descuentoComsion = calcularComision(grupo.getEmpresa().getId(), totalProvisional);
                        if(descuentoComsion<0){
                            descuentoComsion=0.0;
                        }

                        grupo.setDescuentoComision(descuentoComsion);

                        Double total = totalProvisional - descuentoCuentaCorriente - descuentoComsion;
                        if(total<0){
                            total=0.0;
                        }
                        grupo.setTotal(total);
                    }

                    return grupo;
                })
                .collect(Collectors.toList());
    }

    public List<Grupo> listarGruposWine() {
        List<Grupo> grupos = grupoRepository.findByTipoVentaWne();

        return grupos.stream()
                .map(grupo -> {
                    List<Venta> ventasRestaurante = grupo.getVentas().stream()
                            .filter(venta -> venta.getTipoVenta().equals(TipoVenta.WINE))
                            .collect(Collectors.toList());

                    double totalVentas = ventasRestaurante.stream()
                            .mapToDouble(Venta::getMontoVenta)
                            .sum();
                    grupo.setMontoVentasGrupo(totalVentas);

                    Double totalProvisional = totalVentas + grupo.getMontoMesa();
                    double descuentoCuentaCorriente = calcularCuentaCorriente(totalProvisional, grupo.getEmpresa().getId());
                    grupo.setDescuentoCtaCorriente(descuentoCuentaCorriente);
                    double descuentoComsion = calcularComision(grupo.getEmpresa().getId(), totalProvisional);
                    grupo.setDescuentoComision(descuentoComsion);
                    Double total = totalProvisional - descuentoCuentaCorriente - descuentoComsion;
                    grupo.setTotal(total);

                    return grupo;
                })
                .collect(Collectors.toList());
    }


    public Double calcularCuentaCorriente(Double monto ,Long id){
        Empresa empresa=empresaRepository.findById(id).orElse(null);

        double descuento=monto* (empresa.getComision() / 100.0);

        return descuento;
    }

    public Double calcularComision(Long id ,Double monto) {
        Empresa empresa = empresaRepository.findById(id).orElse(null);
        if (empresa != null) {
            Double totalComision = 0.0;
            List<EmpleadoEmpresa> empleados = empresa.getEmpleadoEmpresa();


            for (EmpleadoEmpresa empleado : empleados) {

                Double comision = empleado.getComision();

                totalComision += comision;
            }

            //return totalComision + empresa.getComision();

            Double descuento=monto *(totalComision/100.0);
            return descuento;
        } else {
            // Manejar el caso en que la empresa no exista
            throw new IllegalArgumentException("No se encontró la empresa con el ID proporcionado");
        }
    }


    public Grupo actualizarGrupo(Long id, NuevoGrupoDto grupoRequest) {
        Grupo grupoExistente = grupoRepository.findById(id).orElse(null);

        if (grupoExistente != null) {
            if (grupoRequest.getEstadoGrupo() != null) {
                grupoExistente.setEstadoGrupo(grupoRequest.getEstadoGrupo());
            }

            if (grupoRequest.getMontoMesa()!= null) {
                grupoExistente.setMontoMesa(grupoRequest.getMontoMesa());
            }

            if (grupoRequest.getComensales() != null) {
                grupoExistente.setComensales(grupoRequest.getComensales());
            }

            return grupoRepository.save(grupoExistente);
        }

        return null;
    }

    public void crearNuevoGrupo(NuevoGrupoDto nuevoGrupoDto) {
        // Buscar la empresa por su id
        Optional<Empresa> optionalEmpresa = empresaRepository.findById(nuevoGrupoDto.getEmpresa());

        // Verificar si la empresa existe antes de proceder
        if (optionalEmpresa.isPresent()) {
            // Obtener la empresa desde el Optional
            Empresa empresa = optionalEmpresa.get();

            // CrearGrupoConBuilder
            Grupo nuevoGrupo = Grupo.builder()
                    .comensales(nuevoGrupoDto.getComensales())
                    .empresa(empresa)
                    .estadoGrupo(EstadoGrupo.ABIERTO)
                    .montoVentasGrupo(0)
                    .montoMesa(0.0)
                    .descuentoCtaCorriente(0.0)
                    .descuentoComision(0.0)
                    .total(0.0)

                    .tipoGrupo(TipoGrupo.Restaurante)
                    // Agrega otros atributos del grupo según tus necesidades
                    .build();
            grupoRepository.save(nuevoGrupo);
        } else {
            // Manejar el caso en que la empresa no existe (lanzar excepción, devolver un mensaje de error, etc.)
            throw new Error("No se encontró la empresa con el ID proporcionado");
        }
    }
}
