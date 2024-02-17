package com.paquete.Bodega.DTO;

import com.paquete.Bodega.Enum.FormaPago;
import com.paquete.Bodega.models.Grupo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class VentaDto {
    private Date fechaVenta;
    private Double montoVenta;

    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;
    private Long  grupoId;
    private Long tipoVentaId;
    private List<DetalleVentaDto> detalles;
}
