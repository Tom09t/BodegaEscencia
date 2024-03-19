package com.paquete.Bodega.DTO;

import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.Enum.TipoGrupo;
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
    private Double montoMesa;
private TipoGrupo tipoGrupo;

}