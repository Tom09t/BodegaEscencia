package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "Empresa")
public class Empresa extends BaseEntidad{


    private String nombreEmpresa;

    private Double comision;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "empresa_id", referencedColumnName = "id")
    private List<EmpleadoEmpresa> empleadoEmpresa = new ArrayList<>();

    /*OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "datellePedido_id", referencedColumnName = "id")
    private List<EmpleadoEmpresa> empleadoEmpresa = new ArrayList<>();*/


    public void agregarEmpleadoEmpresa(EmpleadoEmpresa empleadoEmpresa) {
        if (this.empleadoEmpresa == null) {
            this.empleadoEmpresa = new ArrayList<>();
        }
        this.empleadoEmpresa.add(empleadoEmpresa);
    }

}
