package com.paquete.Bodega.DTO;

import com.paquete.Bodega.Enum.FormaPago;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VentaComboDto {
    private Long grupoId;
    private Date fechaVenta;
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;
    private List<ComboVentaDto>combos;

}
