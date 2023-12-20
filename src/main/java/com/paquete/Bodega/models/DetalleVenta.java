package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detalle_venta")
public class DetalleVenta extends BaseEntidad{

    private Integer cantidad;
    private Double subTotal;

    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @OneToOne
    @JoinColumn(name = "forma_pago_id")
    private FormaDePago formaPago;

    @OneToMany(mappedBy = "detalleVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();
}
