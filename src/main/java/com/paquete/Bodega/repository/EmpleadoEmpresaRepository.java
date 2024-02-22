package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.EmpleadoEmpresa;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoEmpresaRepository extends BaseRepository<EmpleadoEmpresa,Long>{

    boolean existsByNombreEmpleado(String nombreEmpleado);
}
