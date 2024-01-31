package com.paquete.Bodega.DTO;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaEmpresaDto {
    private String nombreEmpresa;
    private List<EmpleadoEmpresa> empleadoEmpresa;

}
