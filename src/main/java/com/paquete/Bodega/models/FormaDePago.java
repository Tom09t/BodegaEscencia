package com.paquete.Bodega.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "forma_pago")
public class FormaDePago extends BaseEntidad{

    private String nombreFormaPago;
}
