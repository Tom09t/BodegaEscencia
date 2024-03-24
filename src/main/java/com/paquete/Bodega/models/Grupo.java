package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paquete.Bodega.Enum.EstadoGrupo;
import com.paquete.Bodega.Enum.EstadoGrupoWine;
import com.paquete.Bodega.Enum.TipoGrupo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Grupo")
public class Grupo extends BaseEntidad{


    private double montoVentasGrupo;
    @NotNull
    private int comensales;
   // @NotNull
    private EstadoGrupo estadoGrupo;

    private EstadoGrupoWine estadoGrupoWine;

    private Double descuentoCtaCorriente;

    private Double descuentoComision;
    private Double total;

private LocalDateTime fechaCreacion;



   // @NotNull
    private Double montoMesa;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private Empresa empresa;

    @JsonIgnore
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Venta> ventas = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Regalo> regalos = new ArrayList<>();


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
