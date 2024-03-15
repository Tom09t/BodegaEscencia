package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "combos")
public class Combo extends BaseEntidad{

    @NotNull
    private String nombreCombo;

    @NotNull
    private Double precioTotal;


    @JsonManagedReference
    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "producto_combo",
            joinColumns = @JoinColumn(name = "combo_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productos = new ArrayList<>();




    @OneToMany(mappedBy = "combo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleCombo> detalles = new ArrayList<>();

    private List<Integer> cantidadesXproductos =new ArrayList<>();











}
