package com.paquete.Bodega.DTO;

import com.paquete.Bodega.models.Producto;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboDto {
    private String nombreCombo;
    private List<Long> productosIds;
    private int cantidad1;
    private int cantidad2;
    private int cantidad3;

}
