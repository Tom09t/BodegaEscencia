package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends BaseRepository<Producto,Long> {

    boolean existsByNombreProducto(String nombreProducto);


    Optional<Producto> findByNombreProducto(String nombreProducto);
}
