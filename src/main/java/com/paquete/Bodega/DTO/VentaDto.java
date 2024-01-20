package com.paquete.Bodega.DTO;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class VentaDto {
    private Date fechaVenta;
    private Double montoVenta;
    private Long formaDePagoId;
    private Long tipoVentaId;
    private List<DetalleVentaDto> detalles;
}
