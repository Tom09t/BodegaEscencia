package com.paquete.Bodega.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboVentaDto {
    private Long comboId;
    private int cantidad;
}
