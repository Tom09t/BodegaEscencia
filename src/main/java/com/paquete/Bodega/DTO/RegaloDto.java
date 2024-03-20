package com.paquete.Bodega.DTO;

import com.paquete.Bodega.models.DetalleRegalo;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RegaloDto {

    private Date fecha;

    private List<DetalleRegaloDto> detallesRegalo;
    private Long  grupoId;

}
