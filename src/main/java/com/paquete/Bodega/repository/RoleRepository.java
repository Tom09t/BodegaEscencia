package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
