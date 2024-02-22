package com.paquete.Bodega.DTO;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevaEmpresaDto {
    private String nombreEmpresa;
    @NotNull
    private double comision;
    private List<EmpleadoEmpresa> empleadoEmpresa;

}
