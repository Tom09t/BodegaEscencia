package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends BaseRepository<Stock,Long> {
}
