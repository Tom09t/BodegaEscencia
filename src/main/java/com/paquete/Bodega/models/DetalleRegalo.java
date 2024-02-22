package com.paquete.Bodega.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@Table(name = "detalle_regalo")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleRegalo extends BaseEntidad{



    @ManyToOne
    @JoinColumn(name = "regalo_id", nullable = false)
    @JsonIgnore
    private Regalo regalo;


    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @NotNull
    private int cantidad;


}
