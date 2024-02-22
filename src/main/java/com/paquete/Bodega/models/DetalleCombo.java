package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleCombo extends BaseEntidad{
    @ManyToOne
    @JoinColumn(name = "venta_id")
    @JsonIgnore
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "combo_id")
    @NotNull
    private Combo combo;
    private Double subTotal;

    @NotNull
    private int cantidad;


}
