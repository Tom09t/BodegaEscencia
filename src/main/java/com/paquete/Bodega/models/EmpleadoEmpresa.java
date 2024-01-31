package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Table(name = "EmpleadoEmpresa")
public class EmpleadoEmpresa extends BaseEntidad{

    @Column(name = "nombre_empleado", nullable = false)
    private String nombreEmpleado;
    private double comision;


    @ManyToOne
    @JoinColumn(name = "empresa_id")
    @JsonIgnore
    private Empresa empresa;


}
