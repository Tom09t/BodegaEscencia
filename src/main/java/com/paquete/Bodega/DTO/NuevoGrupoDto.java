package com.paquete.Bodega.DTO;

import com.paquete.Bodega.Enum.EstadoGrupo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevoGrupoDto {

    private Integer comensales;
    private Long empresa;
    private EstadoGrupo estadoGrupo;
    private Double totalMesa;

}