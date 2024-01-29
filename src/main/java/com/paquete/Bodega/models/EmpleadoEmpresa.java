package com.paquete.Bodega.models;

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


    private String nombreEmpleado;
    private double comision;


}
