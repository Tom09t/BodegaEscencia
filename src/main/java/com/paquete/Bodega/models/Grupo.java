package com.paquete.Bodega.models;

import com.paquete.Bodega.Enum.EstadoGrupo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Grupo")
public class Grupo extends BaseEntidad{

    private double montoGrupo;
    private int comensales;
    private EstadoGrupo estadoGrupo;

    private Double montoMesa;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    @Builder.Default
    private Empresa empresa = new Empresa();

      /*  @OneToOne(cascade= CascadeType.ALL, orphanRemoval = true, fetch= FetchType.EAGER)
    @JoinColumn(name = "mesa_id", referencedColumnName = "id", nullable = true)
    @Builder.Default
    private Mesa mesa = new Mesa();*/
/*
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoGrupo_id", referencedColumnName = "id")
    @Builder.Default
    private EstadoGrupo estadoGrupo = new EstadoGrupo();*/


}
