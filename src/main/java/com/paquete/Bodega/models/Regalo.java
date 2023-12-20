package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Regalos")
public class Regalo extends BaseEntidad{

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;
}
