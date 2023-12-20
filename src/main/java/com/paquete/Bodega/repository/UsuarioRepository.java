package com.paquete.Bodega.repository;

import com.paquete.Bodega.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findByUsername(String username);

    @Query("select u from Usuario u where u.username = ?1")
    Optional<Usuario> getName(String username);
}

