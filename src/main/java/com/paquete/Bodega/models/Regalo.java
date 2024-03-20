package com.paquete.Bodega.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Regalos")
public class Regalo extends BaseEntidad{


    private Date fecha;


    @OneToMany(mappedBy = "regalo", cascade = CascadeType.ALL, orphanRemoval = true)
   private List<DetalleRegalo>detalleRegalos= new ArrayList<>();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    @JsonIgnore
    private Grupo grupo;

}
