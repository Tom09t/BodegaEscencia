package com.paquete.Bodega.services.serviceimpl;

import com.paquete.Bodega.models.Stock;
import com.paquete.Bodega.repository.BaseRepository;
import com.paquete.Bodega.repository.StockRepository;
import com.paquete.Bodega.services.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StockServiceImpl extends BaseServiceImpl<Stock,Long> implements StockService {

    //Inyeccion de dependencias
    @Autowired
    StockRepository stockRepository;

    //Configuraciones necesarias
    public StockServiceImpl(BaseRepository<Stock, Long> baseRepository, StockRepository stockRepository) {
        super(baseRepository);
        this.stockRepository = stockRepository;
    }
}
