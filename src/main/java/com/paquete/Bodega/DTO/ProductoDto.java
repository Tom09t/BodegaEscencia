package com.paquete.Bodega.DTO;

import lombok.Data;

@Data
public class ProductoDto {
    private Long id;
    private String nombreProducto;
    private Double precio;
    private int stock;
    private int stockRegalo;

}
