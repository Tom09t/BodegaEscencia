package com.paquete.Bodega.repository;
import com.paquete.Bodega.models.TipoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDeVentaRepository extends BaseRepository<TipoVenta,Long> {
}
