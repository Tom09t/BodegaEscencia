package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Combo;
import com.paquete.Bodega.models.Producto;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComboRepository extends BaseRepository<Combo, Long>{
    boolean existsByNombreCombo(String nombreCombo);


    Optional<Combo> findByNombreCombo(String nombreCombo);

}
