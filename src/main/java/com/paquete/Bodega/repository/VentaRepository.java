package com.paquete.Bodega.repository;
import com.paquete.Bodega.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends BaseRepository<Venta,Long> {

}
