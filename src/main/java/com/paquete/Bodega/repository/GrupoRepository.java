package com.paquete.Bodega.repository;

import com.paquete.Bodega.Enum.TipoVenta;
import com.paquete.Bodega.models.Grupo;
import com.paquete.Bodega.models.Venta;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GrupoRepository extends BaseRepository<Grupo, Long>{

    @Query("SELECT DISTINCT g FROM Grupo g JOIN g.ventas v WHERE 'RESTAURANTE' IN (SELECT tipoVenta FROM Venta WHERE grupo = g)")
    List<Grupo> findByTipoVentaRestaurante();

    @Query("SELECT DISTINCT g FROM Grupo g JOIN g.ventas v WHERE 'WINE' IN (SELECT tipoVenta FROM Venta WHERE grupo = g)")
    List<Grupo> findByTipoVentaWne();
}



