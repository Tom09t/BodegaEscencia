package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto extends BaseEntidad{

    private Date fechaBajaProducto;
    @Column(name = "nombre")

    @NotNull
    private String nombreProducto;
    @NotNull
    private Double precio;
    @NotNull
    private int stock;
    @Column(name = "stock_regalo")
    @NotNull
    private int stockRegalo;

   /* @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "detalle_venta_id", referencedColumnName = "id", nullable = true)
    private DetalleVenta detalleVenta;*/
    @JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

@JsonIgnore
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleRegalo> detallesRegalo;


  @JsonBackReference
   @JsonIgnore
  @ManyToMany(mappedBy = "productos")
    private List<Combo> combos = new ArrayList<>();


   /* @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Regalo> regalos = new ArrayList<>();*/


}
