package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.FormaDePago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormaDePagoRepository extends BaseRepository<FormaDePago,Long> {
}
