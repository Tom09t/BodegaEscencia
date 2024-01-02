package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "formaPago_id", referencedColumnName = "id")
    @Builder.Default
    private FormaDePago formaDePago = new FormaDePago();

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tipo_venta_id")
    private TipoVenta tipoVenta;;
}
