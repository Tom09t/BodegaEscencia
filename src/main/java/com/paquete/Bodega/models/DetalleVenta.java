package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paquete.Bodega.DTO.ProductoDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



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
    @JsonIgnore
    private Venta venta;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto")
    private Producto producto;




/*    @OneToMany(mappedBy = "detalleVenta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Producto> productos = new ArrayList<>();*/
}
