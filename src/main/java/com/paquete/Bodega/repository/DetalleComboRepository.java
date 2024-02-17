package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.DetalleCombo;
import com.paquete.Bodega.models.DetalleRegalo;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleComboRepository extends BaseRepository<DetalleCombo, Long>{

    boolean existsByVentaIdAndId(Long ventaId, Long id);
}
