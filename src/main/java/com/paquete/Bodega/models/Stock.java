package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
public class Stock extends BaseEntidad{

    private Integer cantidad;


    @OneToOne
    @JoinColumn(name = "producto_id", unique = true)
    private Producto producto;

}
