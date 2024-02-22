package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "EmpleadoEmpresa")
public class EmpleadoEmpresa extends BaseEntidad{

    @Column(name = "nombre_empleado", nullable = false)
    @NotNull
    private String nombreEmpleado;
    @NotNull
    private double comision;


    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnore
    private Empresa empresa;


}
