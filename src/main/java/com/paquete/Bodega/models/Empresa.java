package com.paquete.Bodega.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "datellePedido_id", referencedColumnName = "id")
    @Builder.Default
    private List<EmpleadoEmpresa> empleadoEmpresa = new ArrayList<>();

    public void agregarEmpleadoEmpresa(EmpleadoEmpresa empleadoEmpresa) {
        this.empleadoEmpresa.add(empleadoEmpresa);
    }

}
