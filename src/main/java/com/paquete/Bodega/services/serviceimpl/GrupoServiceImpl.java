package com.paquete.Bodega.services.serviceimpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.paquete.Bodega.DTO.NuevoGrupoDto;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.Enum.EstadoGrupoWine;
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
import jakarta.servlet.ServletOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import static com.paquete.Bodega.Enum.FormaPago.*;


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


    public List<Grupo> listarTodosGrupos() {
        List<Grupo> grupos = grupoRepository.findAll();

        for (Grupo grupo : grupos) {

            List<Venta> ventasGrupo = grupo.getVentas();


            double totalVentas = ventasGrupo.stream()
                    .mapToDouble(Venta::getMontoVenta)
                    .sum();

            // Actualizar el total de ventas del grupo en la base de datos
            grupo.setTotal(totalVentas);
            grupoRepository.save(grupo);
        }

        return grupos;
    }


    public List<Grupo> listarGruposRestaurante() {
        List<Grupo> grupos = grupoRepository.findAll();


        for (Grupo grupo : grupos) {
            List<Venta> ventasRestaurante = grupo.getVentas().stream()
                    .filter(venta -> venta.getTipoVenta().equals(TipoVenta.RESTAURANTE))
                    .collect(Collectors.toList());

            if (!ventasRestaurante.isEmpty()) { // Si el grupo tiene ventas de vino
                // Realizar cálculos adicionales
                double totalVentas = ventasRestaurante.stream()
                        .mapToDouble(Venta::getMontoVenta)
                        .sum();

                if (totalVentas < 0) {
                    totalVentas = 0;
                }

                grupo.setMontoVentasGrupo(totalVentas);

                Double totalProvisional = totalVentas + grupo.getMontoMesa();

                double descuentoCuentaCorriente = calcularCuentaCorriente(totalProvisional, grupo.getEmpresa().getId());
                if (descuentoCuentaCorriente < 0) {
                    descuentoCuentaCorriente = 0.0;
                }
                grupo.setDescuentoCtaCorriente(descuentoCuentaCorriente);

                double descuentoComsion = calcularComision(grupo.getEmpresa().getId(), totalProvisional);
                if (descuentoComsion < 0) {
                    descuentoComsion = 0.0;
                }
                grupo.setDescuentoComision(descuentoComsion);

                Double total = totalProvisional - descuentoCuentaCorriente - descuentoComsion;
                if (total < 0) {
                    total = 0.0;
                }

                grupo.setTotal(total);


            }

    }
        return grupos;
    }
        public List<Grupo> listarGruposWine() {
            List<Grupo> grupos = grupoRepository.findAll();


            for (Grupo grupo : grupos) {
                List<Venta> ventasWine = grupo.getVentas().stream()
                        .filter(venta -> venta.getTipoVenta().equals(TipoVenta.WINE))
                        .collect(Collectors.toList());

                if (!ventasWine.isEmpty()) { // Si el grupo tiene ventas de vino
                    // Realizar cálculos adicionales
                    double totalVentas = ventasWine.stream()
                            .mapToDouble(Venta::getMontoVenta)
                            .sum();

                    if (totalVentas < 0) {
                        totalVentas = 0;
                    }

                    grupo.setMontoVentasGrupo(totalVentas);

                    Double totalProvisional = totalVentas + grupo.getMontoMesa();

                    double descuentoCuentaCorriente = calcularCuentaCorriente(totalProvisional, grupo.getEmpresa().getId());
                    if (descuentoCuentaCorriente < 0) {
                        descuentoCuentaCorriente = 0.0;
                    }


                    double descuentoComsion = calcularComision(grupo.getEmpresa().getId(), totalProvisional);
                    if (descuentoComsion < 0) {
                        descuentoComsion = 0.0;
                    }
                    grupo.setDescuentoComision(descuentoComsion);

                    Double total = totalProvisional - descuentoComsion;
                    if (total < 0) {
                        total = 0.0;
                    }
                    grupo.setTotal(total);
                }

            }
            return grupos;
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
            if (grupoRequest.getEstadoGrupoWine() != null) {
                grupoExistente.setEstadoGrupoWine(grupoRequest.getEstadoGrupoWine());
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



    public void crearGrup(NuevoGrupoDto nuevoGrupoDto) {
        // Buscar la empresa por su id
        Optional<Empresa> optionalEmpresa = empresaRepository.findById(nuevoGrupoDto.getEmpresa());

        // Verificar si la empresa existe antes de proceder
        if (optionalEmpresa.isPresent()) {
            // Obtener la empresa desde el Optional
            Empresa empresa = optionalEmpresa.get();

            LocalDateTime fechaCreacion = LocalDateTime.now();

            // CrearGrupoConBuilder
            Grupo nuevoGrupo = Grupo.builder()
                    .comensales(nuevoGrupoDto.getComensales())
                    .empresa(empresa)
                    .fechaCreacion(fechaCreacion)
                    .estadoGrupo(EstadoGrupo.ABIERTO)
                    .estadoGrupoWine(EstadoGrupoWine.ABIERTOWINE)
                    .montoVentasGrupo(0)
                    .montoMesa(0.0)
                   // .tipoGrupo(TipoGrupo.Restaurante)
                    // Agrega otros atributos del grupo según tus necesidades
                    .build();
            grupoRepository.save(nuevoGrupo);




        } else {
            // Manejar el caso en que la empresa no existe (lanzar excepción, devolver un mensaje de error, etc.)
            throw new Error("No se encontró la empresa con el ID proporcionado");
        }
    }

    public void crearGrupoWine(NuevoGrupoDto nuevoGrupoDto) {
        // Buscar la empresa por su id
        Optional<Empresa> optionalEmpresa = empresaRepository.findById(nuevoGrupoDto.getEmpresa());

        // Verificar si la empresa existe antes de proceder
        if (optionalEmpresa.isPresent()) {

        } else{
            // Obtener la empresa desde el Optional
            Empresa empresa = optionalEmpresa.get();

            // CrearGrupoConBuilder
            Grupo nuevoGrupo = Grupo.builder()
                    .empresa(empresa)
                    .estadoGrupo(EstadoGrupo.ABIERTO)
                    .montoVentasGrupo(0)
                    //.tipoGrupo(TipoGrupo.Wine)
                    // Agrega otros atributos del grupo según tus necesidades
                    .build();

            // Guardar el grupo en la base de datos
            grupoRepository.save(nuevoGrupo);
        }

        }


    public void generarPdf(ServletOutputStream outputStream) {
        Document document = new Document(PageSize.A4);

        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Primera tabla para mostrar grupos y ventas individuales
            PdfPTable tableGrupos = new PdfPTable(2); // 2 columnas
            tableGrupos.setWidthPercentage(100);

            // Agregar encabezado de la tabla de grupos
            PdfPCell headerCellGrupos = new PdfPCell(new Paragraph("Grupos y Ventas"));
            headerCellGrupos.setColspan(2);
            headerCellGrupos.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableGrupos.addCell(headerCellGrupos);

            // Encabezados de columna
            tableGrupos.addCell("Grupo");
            tableGrupos.addCell("Ventas");

            // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Obtener los grupos del día
            List<Grupo> gruposDelDia = listarTodosGrupos().stream()
                    .filter(grupo -> {
                        LocalDateTime fechaCreacion = grupo.getFechaCreacion();
                        return fechaCreacion != null && fechaCreacion.toLocalDate().equals(fechaActual);
                    })
                    .collect(Collectors.toList());

            // Agregar las ventas de cada grupo del día a la tabla de grupos
            for (Grupo grupo : gruposDelDia) {
                tableGrupos.addCell(grupo.getEmpresa().getNombreEmpresa()); // Nombre del grupo
                tableGrupos.addCell(String.valueOf(grupo.getTotal())); // Total del grupo
            }

            document.add(tableGrupos);

            // Segunda tabla para mostrar el total de todas las ventas del día
            PdfPTable tableTotal = new PdfPTable(1); // 1 columna
            tableTotal.setWidthPercentage(100);

            // Agregar fila con el total de todas las ventas del día actual
            double totalVentasDia = gruposDelDia.stream().mapToDouble(Grupo::getTotal).sum();
            PdfPCell totalVentasDiaCell = new PdfPCell(new Phrase("Total de ventas  " + totalVentasDia));
            totalVentasDiaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalVentasDiaCell.setBorder(Rectangle.NO_BORDER); // Sin borde
            tableTotal.addCell(totalVentasDiaCell);

            tableTotal.setSpacingAfter(20f);
            document.add(tableTotal);

            // Agregar espacio entre las tablas
            document.add(Chunk.NEWLINE);

            // Tabla de desglose de ventas
            PdfPTable tableDesgloseVentas = generarDesgloseDeVentas();
            tableDesgloseVentas.setSpacingBefore(20f);
            document.add(tableDesgloseVentas);

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }


    private PdfPTable generarDesgloseDeVentas(){

    LocalDate fechaActual = LocalDate.now();

    // Obtener todos los grupos del día actual
    List<Grupo> gruposDelDia = listarTodosGrupos().stream()
            .filter(grupo -> {
                LocalDateTime fechaCreacion = grupo.getFechaCreacion();
                return fechaCreacion != null && fechaCreacion.toLocalDate().equals(fechaActual);
            })
            .collect(Collectors.toList());



    Double VentasEfectivoR = 0.0;
    Double VentasCuentaCorrienteR=0.0;
    Double VentasTransferenciaR=0.0;
    Double VentasEfectivoW = 0.0;
    Double VentasCuentaCorrienteW=0.0;
    Double VentasTransferenciaW=0.0;



    for (Grupo grupo : gruposDelDia) {
        for (Venta venta : grupo.getVentas()) {
            switch (venta.getFormaPago()) {
                case EFECTIVO:
                    VentasEfectivoR += venta.getMontoVenta();
                    break;
                case CUENTA_CORRIENTE:
                    VentasCuentaCorrienteR += venta.getMontoVenta();
                    break;
                case TRANSFERENCIA:
                    VentasTransferenciaR += venta.getMontoVenta();
                    break;

            }
        }
    }




    Double totalVentasEfectivo = VentasEfectivoR+VentasEfectivoW;
    Double totalVentasCuentaCorriente=VentasCuentaCorrienteR+VentasCuentaCorrienteW;
    Double totalVentasTransferencia=VentasTransferenciaR+VentasTransferenciaW;

    // Crear una tabla para mostrar los totales de ventas por forma de pago
    PdfPTable tableDesgloseVentas = new PdfPTable(2);
    tableDesgloseVentas.setWidthPercentage(100);

    // Encabezado
    PdfPCell headerCell = new PdfPCell(new Paragraph("Desglose de Ventas"));
    headerCell.setColspan(2);
    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
    tableDesgloseVentas.addCell(headerCell);

    // Detalles de ventas
    tableDesgloseVentas.addCell("Efectivo");
    tableDesgloseVentas.addCell(String.valueOf(totalVentasEfectivo));

    tableDesgloseVentas.addCell("Cuenta Corriente");
    tableDesgloseVentas.addCell(String.valueOf(totalVentasCuentaCorriente));

    tableDesgloseVentas.addCell("Transferencia");
    tableDesgloseVentas.addCell(String.valueOf(totalVentasTransferencia));

    return tableDesgloseVentas;

}


}
































