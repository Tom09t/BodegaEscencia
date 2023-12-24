package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto extends BaseEntidad{

    private Date fechaBajaProducto;
    @Column(name = "nombre")
    private String nombreProducto;
    private Double precio;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "detalle_venta_id", referencedColumnName = "id", nullable = true)
    private DetalleVenta detalleVenta;


    @ManyToMany
    @JoinTable(
            name = "producto_combo",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "combo_id"))
    private Set<Combo> combos = new HashSet<>();

    @OneToOne(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stock stock;


    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Regalo> regalos = new ArrayList<>();


}
