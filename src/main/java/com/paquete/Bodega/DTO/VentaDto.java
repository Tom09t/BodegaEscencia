package com.paquete.Bodega.DTO;

import com.paquete.Bodega.Enum.FormaPago;
import com.paquete.Bodega.Enum.TipoVenta;
import com.paquete.Bodega.models.Grupo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;
@Data
public class VentaDto {

    private Double montoVenta;
    @Enumerated(EnumType.STRING)
    @NotNull
    private FormaPago formaPago;
    @NotNull
    private Long  grupoId;



    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoVenta tipoVenta;
    private List<DetalleVentaDto> detalles;
}
