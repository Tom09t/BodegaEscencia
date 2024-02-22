package com.paquete.Bodega.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paquete.Bodega.Enum.FormaPago;
import com.paquete.Bodega.Enum.TipoVenta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Venta extends BaseEntidad {

    @Column(name = "fecha_venta")
    private LocalDateTime fechaVenta;
    private Double montoVenta;

    private Boolean esCombo;


    /*@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "formaPago_id", referencedColumnName = "id")*/
    @Enumerated(EnumType.STRING)
    @NotNull
    private FormaPago formaPago;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleCombo> detalleCombos = new ArrayList<>();;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    @JsonIgnore
    private Grupo grupo;


    @Enumerated(EnumType.STRING)
    @NotNull
    private TipoVenta tipoVenta;


}
