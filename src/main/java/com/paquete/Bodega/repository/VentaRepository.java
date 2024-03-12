package com.paquete.Bodega.repository;
import com.paquete.Bodega.Enum.TipoVenta;
import com.paquete.Bodega.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends BaseRepository<Venta,Long> {
    List<Venta> findAllByOrderByFechaVentaDesc();

    List<Venta> findByGrupoIdAndTipoVenta(Long grupoId, TipoVenta tipoVenta);

}
