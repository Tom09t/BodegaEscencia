package com.paquete.Bodega.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paquete.Bodega.Enum.FormaPago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta extends BaseEntidad {

    private Date fechaVenta;
    private Double montoVenta;

    private Boolean esCombo;
    private String nombreCombo;

    /*@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "formaPago_id", referencedColumnName = "id")*/
    @Enumerated(EnumType.STRING)
    private FormaPago formaPago;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleCombo> detalleCombos = new ArrayList<>();;


    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
   @JsonIgnore
    private Grupo grupo;
    @ManyToOne
    @JoinColumn(name = "tipo_venta_id")
    private TipoVenta tipoVenta;;
}
