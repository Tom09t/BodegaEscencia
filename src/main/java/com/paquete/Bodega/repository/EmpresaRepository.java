package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Empresa;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa,Long>{

    boolean existsByNombreEmpresa(String nombreEmpresa);
}
