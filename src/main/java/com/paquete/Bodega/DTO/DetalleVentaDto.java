package com.paquete.Bodega.DTO;

import lombok.Data;

@Data
public class DetalleVentaDto {

    private Integer cantidad;
    private Double subTotal;

    private Long productoId;

}
