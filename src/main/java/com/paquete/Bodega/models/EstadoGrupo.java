package com.paquete.Bodega.models;

import com.paquete.Bodega.Enum.EstadoGrupoNombre;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "EstadoGrupo")
public class EstadoGrupo extends BaseEntidad{

    private EstadoGrupoNombre estadoGrupoNombre;

}

