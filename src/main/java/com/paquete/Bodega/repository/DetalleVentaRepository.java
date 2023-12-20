package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Comensales;
import com.paquete.Bodega.models.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleVentaRepository extends BaseRepository<DetalleVenta,Long>{
}
