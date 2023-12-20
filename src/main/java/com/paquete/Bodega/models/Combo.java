package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "combos")
public class Combo extends BaseEntidad{

    private String nombreCombo;

    @ManyToMany(mappedBy = "combos")
    private Set<Producto> productos = new HashSet<>();
}
