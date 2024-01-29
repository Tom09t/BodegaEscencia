package com.paquete.Bodega.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NuevoGrupoDto {

    public int comensales;
    public Long empresa;

}