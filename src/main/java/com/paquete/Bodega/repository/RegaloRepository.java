package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Regalo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegaloRepository extends BaseRepository<Regalo,Long> {

    List<Regalo> findByGrupoId(Long idGrupo);

}
