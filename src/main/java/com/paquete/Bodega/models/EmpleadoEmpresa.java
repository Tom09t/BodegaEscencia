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

    private int nombreEmpleado;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "comision_id", referencedColumnName = "id")
    @Builder.Default
    private Comision comision = new Comision();

}
